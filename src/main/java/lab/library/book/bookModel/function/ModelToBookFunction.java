package lab.library.book.bookModel.function;

import lab.library.book.bookModel.BookCreateModel;
import lab.library.book.entity.Book;
import lab.library.book.entity.Publisher;
import lombok.SneakyThrows;

import java.io.Serializable;
import java.util.function.Function;

public class ModelToBookFunction implements Function<BookCreateModel, Book>, Serializable {
    @Override
    @SneakyThrows
    public Book apply(BookCreateModel model) {
        return Book.builder()
                .id(model.getId())
                .title(model.getTitle())
                .author(model.getAuthor())
                .state(model.getState())
                .dateOfPublication(model.getDateOfPublication())
                .publisher(Publisher.builder()
                        .id(model.getPublisher().getId())
                        .build())
                .build();
    }
}
