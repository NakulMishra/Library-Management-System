
import java.util.Date;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

class Student {
    String name;
    int id_no;
    String Stream;
    String book1, book2;
    int book_no, issuedbook;

    Student(String name, int id_no, String Stream) {
        this.name = name;
        this.id_no = id_no;
        this.Stream = Stream;
        this.book_no = 0;
    }
}

public class library_management {

    static Scanner input;
    Node root;

    // Constructor (FIXED)
    library_management() {
        root = null;
    }

    // BST Node
    class Node {
        String key;
        Node left, right;

        Node(String item) {
            key = item;
            left = right = null;
        }
    }

    // Insert
    void insert(String key) {
        root = insertRec(root, key);
    }

    Node insertRec(Node root, String key) {
        if (root == null) {
            return new Node(key);
        }
        if (key.compareToIgnoreCase(root.key) < 0)
            root.left = insertRec(root.left, key);
        else if (key.compareToIgnoreCase(root.key) > 0)
            root.right = insertRec(root.right, key);
        return root;
    }

    // Search
    boolean containsNode(String key) {
        return containsNodeRecursive(root, key);
    }

    boolean containsNodeRecursive(Node current, String key) {
        if (current == null) return false;
        if (key.equalsIgnoreCase(current.key)) return true;
        return key.compareToIgnoreCase(current.key) < 0
                ? containsNodeRecursive(current.left, key)
                : containsNodeRecursive(current.right, key);
    }

    // Delete
    void deleteKey(String key) {
        root = deleteRec(root, key);
    }

    Node deleteRec(Node root, String key) {
        if (root == null) return null;

        if (key.compareToIgnoreCase(root.key) < 0)
            root.left = deleteRec(root.left, key);
        else if (key.compareToIgnoreCase(root.key) > 0)
            root.right = deleteRec(root.right, key);
        else {
            if (root.left == null) return root.right;
            if (root.right == null) return root.left;

            root.key = minValue(root.right);
            root.right = deleteRec(root.right, root.key);
        }
        return root;
    }

    String minValue(Node root) {
        String min = root.key;
        while (root.left != null) {
            root = root.left;
            min = root.key;
        }
        return min;
    }

    // Print inorder
    void printInorder() {
        printInorder(root);
        System.out.println();
    }

    void printInorder(Node node) {
        if (node == null) return;
        printInorder(node.left);
        System.out.print(node.key + " ");
        printInorder(node.right);
    }

    // Print Tree
    void printTree() {
        printTreeRec(root, 0);
    }

    void printTreeRec(Node node, int space) {
        if (node == null) return;
        space += 5;
        printTreeRec(node.right, space);
        System.out.println();
        for (int i = 5; i < space; i++)
            System.out.print(" ");
        System.out.print("[" + node.key + "]");
        printTreeRec(node.left, space);
    }

    public static void main(String[] args) throws Exception {

        input = new Scanner(System.in);
        library_management tree = new library_management();

        HashMap<String, Integer> hashmapping = new HashMap<>();
        int[][] arr = new int[100][2];

        Student[] students = {
                new Student("Rajvi", 1741078, "B.Tech-ICT"),
                new Student("Krushna", 1741086, "B.Tech-ICT"),
                new Student("Kalagee", 1741052, "B.Tech-ICT")
        };

        BufferedWriter logWriter = new BufferedWriter(new FileWriter("log.txt", true));

        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        Calendar cal = Calendar.getInstance();

        boolean exit = false;
        int indexCounter = 0;

        while (!exit) {

            System.out.println("\n1. Librarian Login");
            System.out.println("2. User Login");
            System.out.println("3. Exit");
            System.out.print("Choice: ");

            int choice = input.nextInt();

            switch (choice) {

                case 1: // Librarian

                    System.out.print("Enter ID: ");
                    String id = input.next();

                    System.out.print("Enter Password: ");
                    String pass = input.next();

                    if (!id.equals("dsa@1") || !pass.equals("abc123")) {
                        System.out.println("Invalid login");
                        break;
                    }

                    boolean libExit = false;

                    while (!libExit) {

                        System.out.println("\n1 Add Book");
                        System.out.println("2 Delete Book");
                        System.out.println("3 Update Quantity");
                        System.out.println("4 Show Books");
                        System.out.println("5 Print Inorder");
                        System.out.println("6 Print Tree");
                        System.out.println("7 Exit");

                        int c = input.nextInt();

                        switch (c) {

                            case 1:
                                System.out.print("Book name: ");
                                String name = input.next();

                                if (tree.containsNode(name)) {
                                    System.out.println("Already exists");
                                    break;
                                }

                                System.out.print("Quantity: ");
                                int qty = input.nextInt();

                                tree.insert(name);
                                hashmapping.put(name, indexCounter);
                                arr[indexCounter][0] = qty;
                                arr[indexCounter][1] = qty;

                                indexCounter++;
                                break;

                            case 2:
                                System.out.print("Book name: ");
                                String del = input.next();

                                if (tree.containsNode(del)) {
                                    tree.deleteKey(del);
                                    hashmapping.remove(del);
                                    System.out.println("Deleted");
                                }
                                break;

                            case 3:
                                System.out.print("Book name: ");
                                String upd = input.next();

                                if (hashmapping.containsKey(upd)) {
                                    System.out.print("Add quantity: ");
                                    int add = input.nextInt();
                                    arr[hashmapping.get(upd)][0] += add;
                                    arr[hashmapping.get(upd)][1] += add;
                                }
                                break;

                            case 4:
                                for (var entry : hashmapping.entrySet()) {
                                    int i = entry.getValue();
                                    System.out.println(entry.getKey()
                                            + " Total:" + arr[i][0]
                                            + " Available:" + arr[i][1]);
                                }
                                break;

                            case 5:
                                tree.printInorder();
                                break;

                            case 6:
                                tree.printTree();
                                break;

                            case 7:
                                libExit = true;
                                break;
                        }
                    }
                    break;

                case 2: // User

                    System.out.print("Enter student ID: ");
                    int sid = input.nextInt();

                    int studentIndex = -1;

                    for (int i = 0; i < students.length; i++) {
                        if (students[i].id_no == sid)
                            studentIndex = i;
                    }

                    if (studentIndex == -1) {
                        System.out.println("Invalid student");
                        break;
                    }

                    System.out.print("Enter book name: ");
                    String book = input.next();

                    if (!tree.containsNode(book)) {
                        System.out.println("Not available");
                        break;
                    }

                    int bookIndex = hashmapping.get(book);

                    if (arr[bookIndex][1] <= 0) {
                        System.out.println("Out of stock");
                        break;
                    }

                    arr[bookIndex][1]--;

                    Date issue = cal.getTime();
                    cal.add(Calendar.SECOND, 10);
                    Date due = cal.getTime();

                    logWriter.write("\nIssued: " + book
                            + " to " + students[studentIndex].name
                            + " at " + formatter.format(issue));

                    System.out.println("Issued successfully");
                    System.out.println("Due: " + formatter.format(due));

                    break;

                case 3:
                    exit = true;
                    break;
            }
        }

        logWriter.close();
        input.close();

        System.out.println("Program ended.");
    }
}
