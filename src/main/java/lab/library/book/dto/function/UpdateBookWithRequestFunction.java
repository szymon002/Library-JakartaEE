package lab.library.book.dto.function;

import lab.library.book.dto.PatchBookRequest;
import lab.library.book.entity.Book;

import java.util.function.BiFunction;

public class UpdateBookWithRequestFunction implements BiFunction<Book, PatchBookRequest, Book> {
    @Override
    public Book apply(Book book, PatchBookRequest patchBookRequest) {
        return Book.builder()
                .id(book.getId())
                .title(patchBookRequest.getTitle())
                .author(patchBookRequest.getAuthor())
                .state(patchBookRequest.getState())
                .dateOfPublication(book.getDateOfPublication())
                .publisher(book.getPublisher())
                .user(book.getUser())
                .build();
    }
}
