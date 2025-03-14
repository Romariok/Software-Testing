package itmo.st.galaxy;

import java.util.Date;
import java.util.LinkedHashMap;
import lombok.Getter;
import lombok.ToString;

@ToString
@Getter
public class Editor {
   private String name;
   private Date birthDate;
   private LinkedHashMap<Book, BookState> books;

   public Editor(String name, Date birthDate) {
      this.name = name;
      this.birthDate = birthDate;
      this.books = new LinkedHashMap<>();
      if (name == null || name.isEmpty()) {
         throw new IllegalArgumentException("Имя редактора не может быть пустым");
      }
      if (birthDate == null) {
         throw new IllegalArgumentException("Дата рождения не может быть пустой");
      }
      if (birthDate.after(new Date())) {
         throw new IllegalArgumentException("Дата рождения не может быть в будущем");
      }
      if (birthDate.before(new Date(0))) {
         throw new IllegalArgumentException("Дата рождения не может быть меньше 0");
      }
   }

   public void addBook(Book book) {
      BookState state;
      if (book.getTitle().equals("Путеводитель по Галактике для автостопщиков")) {
         state = BookState.EXTRORDINARY;
      } else {
         state = BookState.values()[(int) (Math.random() * BookState.values().length)];
      }

      this.books.put(book, state);
   }

}
