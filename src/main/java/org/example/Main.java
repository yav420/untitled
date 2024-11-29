package org.example;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import lombok.Getter;


import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.stream.Collectors;


public class Main {
    public static void main(String[] args) {

        Gson gson = new Gson();
        List<User> users;

        try (Reader reader = new FileReader("books.json")) {
            users = gson.fromJson(reader, new TypeToken<List<User>>() {}.getType());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        //Задание 1
        users.forEach(System.out::println);

        //Задание 2

        Set<Book> books = users.stream().map(User::getFavoriteBooks).flatMap(Collection::stream).collect(Collectors.toSet());

        int booksCount = books.size();
        System.out.println("\n" + booksCount);
        books.forEach(System.out::println);

        //Задание 3

        List<Book> sortedBooks = users.stream().flatMap(user -> user.getFavoriteBooks().stream()).sorted(Comparator.comparingInt(Book::getPublishingYear)).toList();

        System.out.println();
        sortedBooks.forEach(book -> System.out.println(book.getName() + ": " + book.getPublishingYear()));

        //Задание 4

        List<User> usersLikedJane = users.stream().filter(user -> user.getFavoriteBooks().stream().anyMatch(book -> book.getAuthor().equals("Jane Austen"))).toList();

        System.out.println();
        usersLikedJane.forEach(user -> System.out.println(user.getName() + ": " + user.getFavoriteBooks()));


    }
}

@Getter
class User {

    private String name;
    private String surname;
    private String phone;
    private Boolean subscribed;
    private List<Book> favoriteBooks;

    public User() {}

    public User(String name, String surname, String phone, Boolean subscribed) {
        this.name = name;
        this.surname = surname;
        this.phone = phone;
        this.subscribed = subscribed;
    }

    @Override
    public String toString() {
        return name + " " + surname + " " + phone + " " + subscribed + " " + favoriteBooks;
    }
}

@Getter
class Book {

    private String name;
    private String author;
    private Integer publishingYear;
    private String isbn;
    private String publisher;

    public Book() {}

    public Book(String name, String author, Integer publishingYear, String isbn, String publisher) {
        this.name = name;
        this.author = author;
        this.publishingYear = publishingYear;
        this.isbn = isbn;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        return name + " " + author + " " + publishingYear + " " + isbn + " " + publisher;
    }
}