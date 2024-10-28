package lab.library.book.bookModel.converter;

import jakarta.faces.component.UIComponent;
import jakarta.faces.context.FacesContext;
import jakarta.faces.convert.Converter;
import jakarta.faces.convert.FacesConverter;
import lab.library.book.entity.Book;

@FacesConverter("stateConverter")
public class StateConverter implements Converter<Book.State> {
    @Override
    public Book.State getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isBlank()) {
            return null;
        }
        return Book.State.valueOf(value);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Book.State value) {
        return value == null ? "" : value.toString();
    }
}
