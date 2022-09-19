package com.edu.ulab.app.storage;

import com.edu.ulab.app.dto.BookDto;
import com.edu.ulab.app.dto.UserDto;
import com.edu.ulab.app.entity.BookEntity;
import com.edu.ulab.app.entity.UserEntity;
import com.edu.ulab.app.entity.User_Book_Entity;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

/**
 * // создать хранилище в котором будут содержаться данные
 * // продумать логику поиска и сохранения
 * // продумать возможные ошибки
 */
@Slf4j
public class Storage implements StorageInterface
{
    private static final String fileNameForUser = "data/users.dat";
    private static final String fileNameForBook = "data/books.dat";
    private static final String fileNameForUserBook = "data/userBooks.dat";
    private static final Set<UserEntity> USER_ENTITIES = new HashSet<>();
    private static final Set<BookEntity> BOOK_ENTITIES = new HashSet<>();
    private static final Set<User_Book_Entity> USER_BOOK_ENTITIES = new HashSet<>();

    static {
        loadUsersFromFile();
        loadBooksFromFile();
        loadUserBooksFromFile();
    }

    @Override
    public void saveUser(UserEntity userEntity){
        USER_ENTITIES.add(userEntity);
        saveUsersToFile();
    }

    public void updateUser(UserEntity userEntity)
    {
        if(USER_ENTITIES
                .stream()
                .filter(Objects::nonNull)
                .anyMatch(x->x.getId().equals(userEntity.getId()))) {
            Set<UserEntity> userEntities = USER_ENTITIES
                    .stream()
                    .filter(x ->x!=null && !x.getId().equals(userEntity.getId()))
                    .collect(Collectors.toSet());
            USER_ENTITIES.clear();
            userEntities.add(new UserEntity(userEntity.getId(), userEntity.getFullName(), userEntity.getTitle(), userEntity.getAge()));
            USER_ENTITIES.addAll(userEntities);
            saveUsersToFile();
        }else{
            log.info("User with that ID don't exists.");
        }
    }
    @Override
    public UserDto getUserById(Long id) {
        UserEntity userEntity = USER_ENTITIES
                .stream()
                .filter(x ->x!=null && x.getId().equals(id))
                .findFirst()
                .orElse(new UserEntity(0L, null,null,0));
        UserDto userDto = new UserDto();
        userDto.setId(userEntity.getId());
        userDto.setFullName(userEntity.getFullName());
        userDto.setTitle(userEntity.getTitle());
        userDto.setAge(userEntity.getAge());
        return userDto;
    }

    public void deleteUserById(Long userId){
        Set<UserEntity> userEntities = USER_ENTITIES
                .stream()
                .filter(x -> x!=null && !x.getId().equals(userId))
                .collect(Collectors.toSet());
        USER_ENTITIES.clear();
        USER_ENTITIES.addAll(userEntities);
        saveUsersToFile();
        Set<User_Book_Entity> user_book_entities = USER_BOOK_ENTITIES
                .stream()
                .filter(x-> x!= null && !x.getUserId().equals(userId))
                .collect(Collectors.toSet());
        USER_BOOK_ENTITIES.clear();
        USER_BOOK_ENTITIES.addAll(user_book_entities);
        saveUserBookToFile();
        Set<BookEntity> bookEntities = BOOK_ENTITIES
                .stream()
                .filter(x->x!=null && !x.getUserId().equals(userId))
                .collect(Collectors.toSet());
        BOOK_ENTITIES.clear();
        BOOK_ENTITIES.addAll(bookEntities);
        saveBooksToFile();
    }

    private static void saveUsersToFile() {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileNameForUser))) {
            stream.writeObject(USER_ENTITIES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressWarnings("unchecked")
    private static void loadUsersFromFile() {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(fileNameForUser))) {
            Set<UserEntity> loadedUsers = (Set<UserEntity>) stream.readObject();
            USER_ENTITIES.addAll(loadedUsers);
        } catch(FileNotFoundException e){
            log.info("Failed to load users.dat. File not exists yet.");
        }
        catch (EOFException e)
        {
            log.info("File users.dat is empty. Don't have information yet.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void createBook(BookEntity bookEntity){
        BOOK_ENTITIES.add(bookEntity);
        saveBooksToFile();
        USER_BOOK_ENTITIES.add(new User_Book_Entity(bookEntity.getUserId(), bookEntity.getId()));
        saveUserBookToFile();

    }

    public void updateBook(BookEntity bookEntity){
        if(BOOK_ENTITIES
                .stream()
                .filter(Objects::nonNull)
                .anyMatch(x->x.getId().equals(bookEntity.getId()))) {
            Set<BookEntity> bookEntities = BOOK_ENTITIES
                    .stream()
                    .filter(x -> x!=null && !x.getId().equals(bookEntity.getId()))
                    .collect(Collectors.toSet());
            BOOK_ENTITIES.clear();
            bookEntities.add(new BookEntity(bookEntity.getId(), bookEntity.getUserId(), bookEntity.getTitle(), bookEntity.getAuthor(), bookEntity.getPageCount()));
            BOOK_ENTITIES.addAll(bookEntities);
            saveBooksToFile();
        }else{
            log.info("Book with that ID don't exists.");
        }
    }
    @Override
    public BookDto getBookById(Long id) {
        BookEntity bookEntity = BOOK_ENTITIES
                .stream()
                .filter(x -> x!= null && x.getId().equals(id))
                .findFirst()
                .orElse(new BookEntity(0L, 0L, null, null, 0));
        BookDto bookDto = new BookDto();
        bookDto.setId(bookEntity.getId());
        bookDto.setUserId(bookEntity.getUserId());
        bookDto.setAuthor(bookEntity.getAuthor());
        bookDto.setTitle(bookEntity.getTitle());
        bookDto.setPageCount(bookEntity.getPageCount());
        return bookDto;
    }

    public List<BookDto> getBooksByUserId(Long userId) {
        List<Long> bookIdList = USER_BOOK_ENTITIES
                .stream()
                .filter(x ->x!=null && x.getUserId().equals(userId))
                .map(User_Book_Entity::getBookId)
                .toList();
        List<BookEntity> bookEntities = BOOK_ENTITIES
                .stream()
                .filter(x->x!=null &&bookIdList.contains(x.getId()))
                .toList();
        List<BookDto> bookDtos = new ArrayList<>();
        bookEntities.forEach(x->{
            BookDto bookDto = new BookDto();
            bookDto.setId(x.getId());
            bookDto.setUserId(x.getUserId());
            bookDto.setTitle(x.getTitle());
            bookDto.setAuthor(x.getAuthor());
            bookDto.setPageCount(x.getPageCount());
            bookDtos.add(bookDto);
        });
        return bookDtos;
    }

    public void deleteBookById(Long id) {
        Set<User_Book_Entity> user_book_entities = USER_BOOK_ENTITIES
                .stream().filter(x ->x!=null && !x.getBookId().equals(id))
                .collect(Collectors.toSet());
        USER_BOOK_ENTITIES.clear();
        USER_BOOK_ENTITIES.addAll(user_book_entities);
        saveUserBookToFile();
        Set<BookEntity> bookEntities = BOOK_ENTITIES
                .stream()
                .filter(x -> x!=null && !x.getId().equals(id)).collect(Collectors.toSet());
        BOOK_ENTITIES.clear();
        BOOK_ENTITIES.addAll(bookEntities);
        saveBooksToFile();
    }

    private static void saveBooksToFile() {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileNameForBook))) {

            stream.writeObject(BOOK_ENTITIES);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @SuppressWarnings("unchecked")
    private static void loadBooksFromFile() {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(fileNameForBook))) {
            Set<BookEntity> loadedBooks = (Set<BookEntity>) stream.readObject();
            BOOK_ENTITIES.addAll(loadedBooks);
        }catch(FileNotFoundException e){
            log.info("Failed to load books.dat. File not exists yet.");
        } catch (EOFException e)
        {
            log.info("File books.dat is empty. Don't have information yet.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    private static void saveUserBookToFile() {
        try (ObjectOutputStream stream = new ObjectOutputStream(new FileOutputStream(fileNameForUserBook))) {
            stream.writeObject(USER_BOOK_ENTITIES);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @SuppressWarnings("unchecked")
    private static void loadUserBooksFromFile() {
        try (ObjectInputStream stream = new ObjectInputStream(new FileInputStream(fileNameForUserBook))) {
            Set<User_Book_Entity> loadedUserBooks = (Set<User_Book_Entity>) stream.readObject();
            USER_BOOK_ENTITIES.addAll(loadedUserBooks);
        }catch(FileNotFoundException e){
            log.info("Failed to load userBooks.dat. File not exists yet.");
        } catch (EOFException e)
        {
            log.info("File userBooks.dat is empty. Don't have information yet.");
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
