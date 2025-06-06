import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

// Main class to run the Library Management System
public class LibraryManagementSystem {
    public static void main(String[] args) {
        Library library = new Library();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. Remove Book");
            System.out.println("3. Add Member");
            System.out.println("4. Remove Member");
            System.out.println("5. Issue Book");
            System.out.println("6. Return Book");
            System.out.println("7. Search Books");
            System.out.println("8. View All Books");
            System.out.println("9. View All Members");
            System.out.println("10. Save Data");
            System.out.println("11. Load Data");
            System.out.println("12. Exit");
            System.out.print("Enter your choice: ");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addBook(library, scanner);
                    break;
                case 2:
                    removeBook(library, scanner);
                    break;
                case 3:
                    addMember(library, scanner);
                    break;
                case 4:
                    removeMember(library, scanner);
                    break;
                case 5:
                    issueBook(library, scanner);
                    break;
                case 6:
                    returnBook(library, scanner);
                    break;
                case 7:
                    searchBooks(library, scanner);
                    break;
                case 8:
                    viewAllBooks(library);
                    break;
                case 9:
                    viewAllMembers(library);
                    break;
                case 10:
                    saveData(library, scanner);
                    break;
                case 11:
                    loadData(library, scanner);
                    break;
                case 12:
                    running = false;
                    System.out.println("Thank you for using Library Management System!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
        scanner.close();
    }

    private static void addBook(Library library, Scanner scanner) {
        System.out.println("\n=== ADD BOOK ===");
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Book Title: ");
        String title = scanner.nextLine();
        System.out.print("Enter Author: ");
        String author = scanner.nextLine();
        System.out.print("Enter Publisher: ");
        String publisher = scanner.nextLine();
        System.out.print("Enter Year of Publication: ");
        int year = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        Book book = new Book(id, title, author, publisher, year);
        library.addBook(book);
        System.out.println("Book added successfully!");
    }

    private static void removeBook(Library library, Scanner scanner) {
        System.out.println("\n=== REMOVE BOOK ===");
        System.out.print("Enter Book ID: ");
        String id = scanner.nextLine();

        if (library.removeBook(id)) {
            System.out.println("Book removed successfully!");
        } else {
            System.out.println("Book not found or cannot be removed (may be issued).");
        }
    }

    private static void addMember(Library library, Scanner scanner) {
        System.out.println("\n=== ADD MEMBER ===");
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter Member Name: ");
        String name = scanner.nextLine();
        System.out.print("Enter Contact Number: ");
        String contactNumber = scanner.nextLine();
        System.out.print("Enter Email: ");
        String email = scanner.nextLine();

        Member member = new Member(id, name, contactNumber, email);
        library.addMember(member);
        System.out.println("Member added successfully!");
    }

    private static void removeMember(Library library, Scanner scanner) {
        System.out.println("\n=== REMOVE MEMBER ===");
        System.out.print("Enter Member ID: ");
        String id = scanner.nextLine();

        if (library.removeMember(id)) {
            System.out.println("Member removed successfully!");
        } else {
            System.out.println("Member not found or cannot be removed (has issued books).");
        }
    }

    private static void issueBook(Library library, Scanner scanner) {
        System.out.println("\n=== ISSUE BOOK ===");
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine();
        System.out.print("Enter Member ID: ");
        String memberId = scanner.nextLine();

        try {
            System.out.print("Enter Due Date (dd/MM/yyyy): ");
            String dueDateStr = scanner.nextLine();
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
            Date dueDate = dateFormat.parse(dueDateStr);

            if (library.issueBook(bookId, memberId, dueDate)) {
                System.out.println("Book issued successfully!");
            } else {
                System.out.println("Failed to issue book. Check if book and member exist and book is available.");
            }
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please use dd/MM/yyyy format.");
        }
    }

    private static void returnBook(Library library, Scanner scanner) {
        System.out.println("\n=== RETURN BOOK ===");
        System.out.print("Enter Book ID: ");
        String bookId = scanner.nextLine();

        if (library.returnBook(bookId)) {
            System.out.println("Book returned successfully!");
        } else {
            System.out.println("Failed to return book. Check if the book exists and is currently issued.");
        }
    }

    private static void searchBooks(Library library, Scanner scanner) {
        System.out.println("\n=== SEARCH BOOKS ===");
        System.out.println("1. Search by Title");
        System.out.println("2. Search by Author");
        System.out.println("3. Search by ID");
        System.out.print("Enter your choice: ");
        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        System.out.print("Enter search term: ");
        String searchTerm = scanner.nextLine();

        List<Book> results = new ArrayList<>();
        switch (choice) {
            case 1:
                results = library.searchBooksByTitle(searchTerm);
                break;
            case 2:
                results = library.searchBooksByAuthor(searchTerm);
                break;
            case 3:
                Book book = library.getBookById(searchTerm);
                if (book != null) {
                    results.add(book);
                }
                break;
            default:
                System.out.println("Invalid choice.");
                return;
        }

        if (results.isEmpty()) {
            System.out.println("No books found matching your search criteria.");
        } else {
            System.out.println("\nSearch Results:");
            for (Book book : results) {
                System.out.println(book);
            }
        }
    }

    private static void viewAllBooks(Library library) {
        System.out.println("\n=== ALL BOOKS ===");
        List<Book> books = library.getAllBooks();

        if (books.isEmpty()) {
            System.out.println("No books in the library.");
        } else {
            for (Book book : books) {
                System.out.println(book);
            }
        }
    }

    private static void viewAllMembers(Library library) {
        System.out.println("\n=== ALL MEMBERS ===");
        List<Member> members = library.getAllMembers();

        if (members.isEmpty()) {
            System.out.println("No members in the library.");
        } else {
            for (Member member : members) {
                System.out.println(member);
            }
        }
    }

    private static void saveData(Library library, Scanner scanner) {
        System.out.println("\n=== SAVE DATA ===");
        System.out.print("Enter filename to save: ");
        String filename = scanner.nextLine();

        try {
            library.saveToFile(filename);
            System.out.println("Data saved successfully to " + filename);
        } catch (IOException e) {
            System.out.println("Error saving data: " + e.getMessage());
        }
    }

    private static void loadData(Library library, Scanner scanner) {
        System.out.println("\n=== LOAD DATA ===");
        System.out.print("Enter filename to load: ");
        String filename = scanner.nextLine();

        try {
            library.loadFromFile(filename);
            System.out.println("Data loaded successfully from " + filename);
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("Error loading data: " + e.getMessage());
        }
    }
}

// Book class to store book information
class Book implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String author;
    private String publisher;
    private int yearOfPublication;
    private boolean isAvailable;
    private String issuedTo;  // Member ID
    private Date dueDate;

    public Book(String id, String title, String author, String publisher, int yearOfPublication) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publisher = publisher;
        this.yearOfPublication = yearOfPublication;
        this.isAvailable = true;
        this.issuedTo = null;
        this.dueDate = null;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }

    public void setAvailable(boolean available) {
        isAvailable = available;
    }

    public String getIssuedTo() {
        return issuedTo;
    }

    public void setIssuedTo(String memberId) {
        this.issuedTo = memberId;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        StringBuilder sb = new StringBuilder();
        sb.append("Book ID: ").append(id)
          .append(", Title: ").append(title)
          .append(", Author: ").append(author)
          .append(", Publisher: ").append(publisher)
          .append(", Year: ").append(yearOfPublication)
          .append(", Status: ").append(isAvailable ? "Available" : "Issued");

        if (!isAvailable && dueDate != null) {
            sb.append(", Due Date: ").append(dateFormat.format(dueDate));
        }

        return sb.toString();
    }
}

// Member class to store member information
class Member implements Serializable {
    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String contactNumber;
    private String email;
    private List<String> issuedBooks;  // List of Book IDs

    public Member(String id, String name, String contactNumber, String email) {
        this.id = id;
        this.name = name;
        this.contactNumber = contactNumber;
        this.email = email;
        this.issuedBooks = new ArrayList<>();
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<String> getIssuedBooks() {
        return issuedBooks;
    }

    public void addIssuedBook(String bookId) {
        issuedBooks.add(bookId);
    }

    public void removeIssuedBook(String bookId) {
        issuedBooks.remove(bookId);
    }

    public boolean hasIssuedBooks() {
        return !issuedBooks.isEmpty();
    }

    @Override
    public String toString() {
        return "Member ID: " + id +
               ", Name: " + name +
               ", Contact: " + contactNumber +
               ", Email: " + email +
               ", Books Issued: " + issuedBooks.size();
    }
}

// Library class to manage books and members
class Library implements Serializable {
    private static final long serialVersionUID = 1L;

    private Map<String, Book> books;  // Book ID -> Book
    private Map<String, Member> members;  // Member ID -> Member

    public Library() {
        books = new HashMap<>();
        members = new HashMap<>();
    }

    public void addBook(Book book) {
        books.put(book.getId(), book);
    }

    public boolean removeBook(String bookId) {
        Book book = books.get(bookId);
        if (book != null && book.isAvailable()) {
            books.remove(bookId);
            return true;
        }
        return false;
    }

    public void addMember(Member member) {
        members.put(member.getId(), member);
    }

    public boolean removeMember(String memberId) {
        Member member = members.get(memberId);
        if (member != null && !member.hasIssuedBooks()) {
            members.remove(memberId);
            return true;
        }
        return false;
    }

    public boolean issueBook(String bookId, String memberId, Date dueDate) {
        Book book = books.get(bookId);
        Member member = members.get(memberId);

        if (book != null && member != null && book.isAvailable()) {
            book.setAvailable(false);
            book.setIssuedTo(memberId);
            book.setDueDate(dueDate);
            member.addIssuedBook(bookId);
            return true;
        }
        return false;
    }

    public boolean returnBook(String bookId) {
        Book book = books.get(bookId);

        if (book != null && !book.isAvailable()) {
            Member member = members.get(book.getIssuedTo());
            if (member != null) {
                member.removeIssuedBook(bookId);
            }

            book.setAvailable(true);
            book.setIssuedTo(null);
            book.setDueDate(null);
            return true;
        }
        return false;
    }

    public Book getBookById(String id) {
        return books.get(id);
    }

    public Member getMemberById(String id) {
        return members.get(id);
    }

    public List<Book> searchBooksByTitle(String title) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> searchBooksByAuthor(String author) {
        List<Book> result = new ArrayList<>();
        for (Book book : books.values()) {
            if (book.getAuthor().toLowerCase().contains(author.toLowerCase())) {
                result.add(book);
            }
        }
        return result;
    }

    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    public List<Member> getAllMembers() {
        return new ArrayList<>(members.values());
    }

    public void saveToFile(String filename) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filename))) {
            oos.writeObject(this);
        }
    }

    public void loadFromFile(String filename) throws IOException, ClassNotFoundException {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filename))) {
            Library loadedLibrary = (Library) ois.readObject();
            this.books = loadedLibrary.books;
            this.members = loadedLibrary.members;
        }
    }
}