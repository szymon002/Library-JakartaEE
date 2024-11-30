package lab.library.configuration.singleton;

import jakarta.annotation.PostConstruct;
import jakarta.annotation.security.DeclareRoles;
import jakarta.annotation.security.RunAs;
import jakarta.ejb.*;
import jakarta.inject.Inject;
import jakarta.security.enterprise.SecurityContext;
import lab.library.book.entity.Book;
import lab.library.book.entity.Publisher;
import lab.library.book.service.BookService;
import lab.library.book.service.PublisherService;
import lab.library.user.entity.User;
import lab.library.user.entity.UserRoles;
import lab.library.user.service.UserService;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Singleton
@Startup
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@DependsOn("InitializeAdminService")
@DeclareRoles({UserRoles.ADMIN, UserRoles.USER})
@RunAs(UserRoles.ADMIN)
@NoArgsConstructor
public class InitializedData {
    private UserService userService;

    private BookService bookService;

    private PublisherService publisherService;

    @Inject
    private SecurityContext securityContext;

    @EJB
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @EJB
    public void setBookService(BookService bookService) {
        this.bookService = bookService;
    }

    @EJB
    public void setPublisherService(PublisherService publisherService) {
        this.publisherService = publisherService;
    }

    @PostConstruct
    @SneakyThrows
    private void init() {
        System.out.println("test1");

        if (userService.find(UUID.fromString("3214f86a-d656-4021-ac1e-1d562b69034a")).isEmpty()) {
            System.out.println("test2");
            User user1 = User.builder()
                    .id(UUID.fromString("3214f86a-d656-4021-ac1e-1d562b69034a"))
                    .login("user1")
                    .password("user1")
                    .firstName("Szymon")
                    .lastName("Malecki")
                    .birthDate(LocalDate.of(2002, 2, 18))
                    .email("szymon.test@gmail.com")
                    .roles(List.of(UserRoles.USER))
                    .build();

            User user2 = User.builder()
                    .id(UUID.fromString("698ae485-51b2-4629-958f-ac0aa5679b32"))
                    .login("user2")
                    .password("user2")
                    .firstName("Anna")
                    .lastName("Kowalska")
                    .birthDate(LocalDate.of(1998, 5, 12))
                    .email("anna.test@gmail.com")
                    .roles(List.of(UserRoles.USER))
                    .build();

            User user3 = User.builder()
                    .id(UUID.fromString("5b04df4d-21a3-4693-b3b2-abaab7f3dd83"))
                    .login("user3")
                    .password("user3")
                    .firstName("Piotr")
                    .lastName("Nowak")
                    .birthDate(LocalDate.of(1995, 11, 23))
                    .email("piotr.test@gmail.com")
                    .roles(List.of(UserRoles.USER))
                    .build();

            User user4 = User.builder()
                    .id(UUID.fromString("fb9de5f7-87e5-4281-9390-75ded8e3bcff"))
                    .login("admin")
                    .password("admin")
                    .firstName("Ewa")
                    .lastName("Zielińska")
                    .birthDate(LocalDate.of(2001, 7, 15))
                    .email("ewa.test@gmail.com")
                    .roles(List.of(UserRoles.USER, (UserRoles.ADMIN)))
                    .build();

            userService.create(user1);
            userService.create(user2);
            userService.create(user3);
            userService.create(user4);

            Publisher publisher1 = Publisher.builder()
                    .id(UUID.fromString("3b0a33fe-2d05-462c-bce7-032c0a542231"))
                    .name("Wydawnictwo Sowa")
                    .country("Polska")
                    .dateOfCreation(LocalDate.now())
                    .numOfWorkers(500)
                    .build();

            Publisher publisher2 = Publisher.builder()
                    .id(UUID.fromString("f8e63c10-5aaf-4b0d-a918-852c43855b4e"))
                    .name("Wydawnictwo Pszczola")
                    .country("Polska")
                    .dateOfCreation(LocalDate.now())
                    .numOfWorkers(100)
                    .build();

            publisherService.create(publisher1);
            publisherService.create(publisher2);

            Book book1 = Book.builder()
                    .id(UUID.fromString("81fed01e-5b96-48ed-af1e-9b36afdec6bf"))
                    .title("Harry Potter 1")
                    .author("J.K Rowling")
                    .dateOfPublication(LocalDate.of(2003, 2, 18))
                    .state(Book.State.NEW)
                    .publisher(publisher1)
                    .user(user1)
                    .build();

            Book book2 = Book.builder()
                    .id(UUID.fromString("ea74c87a-9516-43e6-b0e6-3e67dc764bdd"))
                    .title("Harry Potter 2")
                    .author("J.K Rowling")
                    .dateOfPublication(LocalDate.of(2005, 2, 18))
                    .state(Book.State.NEW)
                    .publisher(publisher1)
                    .user(user2)
                    .build();

            Book book3 = Book.builder()
                    .id(UUID.fromString("f49564bd-94de-4359-b738-c7e4d5cffb78"))
                    .title("Harry Potter 3")
                    .author("J.K Rowling")
                    .dateOfPublication(LocalDate.of(2007, 2, 18))
                    .state(Book.State.NEW)
                    .publisher(publisher1)
                    .user(user2)
                    .build();

            Book book4 = Book.builder()
                    .id(UUID.fromString("9b377529-a869-41b2-bb05-58ebf3ea1756"))
                    .title("Meg")
                    .author("Jason Statham")
                    .dateOfPublication(LocalDate.of(2010, 2, 15))
                    .state(Book.State.USED)
                    .publisher(publisher2)
                    .user(user3)
                    .build();

            Book book5 = Book.builder()
                    .id(UUID.fromString("0531abb3-ddad-432f-86a2-dad65a3fee3b"))
                    .title("Meg 2")
                    .author("Jason Statham")
                    .dateOfPublication(LocalDate.of(2015, 1, 13))
                    .state(Book.State.NEW)
                    .publisher(publisher2)
                    .user(user4)
                    .build();

            bookService.createForInitializer(book1);
            bookService.createForInitializer(book2);
            bookService.createForInitializer(book3);
            bookService.createForInitializer(book4);
            bookService.createForInitializer(book5);

        }
    }
}
