package lab.library.book.controller.api;

import lab.library.book.dto.GetBookResponse;
import lab.library.book.dto.GetBooksResponse;
import lab.library.book.dto.PatchBookRequest;
import lab.library.book.dto.PutBookRequest;

import java.util.UUID;

public interface BookController {

    GetBooksResponse getBooks();

    GetBooksResponse getUserBooks(UUID id);

    GetBooksResponse getPublisherBooks(UUID id);

    GetBookResponse getBook(UUID id);

    void putBook(UUID id, PutBookRequest request);

    void patchBook(UUID id, PatchBookRequest request);

    void deleteBook(UUID id);
}
