# Usage Guide

## Start the app

Build and run:

```bash
mvn -DskipTests package
java -cp target/app-0.0.1-SNAPSHOT.jar secondhand_marketplace.app.App
```

## Main menu

The menu is numeric. Important options include:

- Register, login, logout
- List items for sale
- Buy and checkout items
- Track orders
- Review purchased items
- Manage items you sell
- Chat and view chat history
- Make offers and view notifications

## Common flows

### Register and login

1. Choose Register and enter username, password, email, and phone.
2. Choose Login and enter the username and password.

### Sell an item

1. Login as a user.
2. Choose "Tambah Barang yang Dijual".
3. Enter name, price, stock, description, and location.
4. The item appears in the global list and in your selling list.

### Buy and checkout

1. Login as a buyer.
2. Choose "Beli Barang" and pick a product ID and quantity.
3. Choose "Checkout Pembelian" to confirm payment.
4. The order status becomes "Diproses" after checkout.

### Track and update shipping

- Buyers use "Lacak Barang Pembelian" to see order status.
- Sellers use "Ubah Status Pengiriman Barang" to update shipping status.

### Review a product

After checkout and shipping steps, choose "Review Produk Pembelian" to add a rating and comment.

### Chat

Use the "Chat" menu to send a message or view chat history with other users.

### Offers and notifications

- Buyers can create a price offer on a product.
- Sellers receive a notification when an offer is created.
