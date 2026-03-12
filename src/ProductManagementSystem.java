import java.util.ArrayList;
import java.util.Optional;
import java.util.Scanner;

public class ProductManagementSystem {

    // Danh sách quản lý sản phẩm
    private static ArrayList<Product> products = new ArrayList<>();

    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {

        int choice;

        do {
            showMenu();
            choice = Integer.parseInt(scanner.nextLine());

            try {
                switch (choice) {

                    case 1:
                        addProduct();
                        break;

                    case 2:
                        displayProducts();
                        break;

                    case 3:
                        updateQuantity();
                        break;

                    case 4:
                        deleteOutOfStock();
                        break;

                    case 5:
                        System.out.println("Thoát chương trình");
                        break;

                    default:
                        System.out.println("Lựa chọn không hợp lệ");
                }

            } catch (InvalidProductException e) {
                // bắt lỗi custom exception
                System.out.println("Lỗi: " + e.getMessage());
            }

        } while (choice != 5);
    }

    // Hiển thị menu
    private static void showMenu() {
        System.out.println("1. Thêm sản phẩm mới");
        System.out.println("2. Hiển thị danh sách sản phẩm");
        System.out.println("3. Cập nhật số lượng theo ID");
        System.out.println("4. Xóa sản phẩm đã hết hàng");
        System.out.println("5. Thoát chương trình");
        System.out.print("Chọn chức năng: ");
    }

    // Thêm sản phẩm
    private static void addProduct() throws InvalidProductException {

        System.out.print("Nhập ID: ");
        int id = Integer.parseInt(scanner.nextLine());

        // kiểm tra trùng ID bằng Stream
        boolean exists = products.stream()
                .anyMatch(p -> p.getId() == id);

        if (exists) {
            throw new InvalidProductException("ID đã tồn tại");
        }

        System.out.print("Tên sản phẩm: ");
        String name = scanner.nextLine();

        System.out.print("Giá: ");
        double price = Double.parseDouble(scanner.nextLine());

        System.out.print("Số lượng: ");
        int quantity = Integer.parseInt(scanner.nextLine());

        System.out.print("Danh mục: ");
        String category = scanner.nextLine();

        Product product = new Product(id, name, price, quantity, category);

        products.add(product);

        System.out.println("Thêm sản phẩm thành công");
    }

    // Hiển thị danh sách sản phẩm
    private static void displayProducts() {

        if (products.isEmpty()) {
            System.out.println("Danh sách trống");
            return;
        }

        System.out.println("\n---------------------------------------------------------------");
        System.out.printf("%-5s %-20s %-10s %-10s %-15s\n",
                "ID", "NAME", "PRICE", "QUANTITY", "CATEGORY");
        System.out.println("---------------------------------------------------------------");

        products.forEach(p -> {
            System.out.printf("%-5d %-20s %-10.2f %-10d %-15s\n",
                    p.getId(),
                    p.getName(),
                    p.getPrice(),
                    p.getQuantity(),
                    p.getCategory());
        });
    }

    // Cập nhật số lượng theo ID
    private static void updateQuantity() throws InvalidProductException {

        System.out.print("Nhập ID cần cập nhật: ");
        int id = Integer.parseInt(scanner.nextLine());
        Optional<Product> optionalProduct =
                products.stream()
                        .filter(p -> p.getId() == id)
                        .findFirst();

        if (!optionalProduct.isPresent()) {
            throw new InvalidProductException("Không tìm thấy sản phẩm");
        }

        Product product = optionalProduct.get();

        System.out.print("Nhập số lượng mới: ");
        int newQuantity = Integer.parseInt(scanner.nextLine());

        product.setQuantity(newQuantity);

        System.out.println("Cập nhật thành công");
    }

    // Xóa sản phẩm có quantity = 0
    private static void deleteOutOfStock() {
        products.removeIf(p -> p.getQuantity() == 0);

        System.out.println("Đã xóa các sản phẩm hết hàng");
    }
}