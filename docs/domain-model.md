# Domain Model

## Core classes

- `Pengguna`: abstract base class for users. Stores username, password, email, and phone.
- `PembeliPenjual`: concrete user type. Acts as both buyer and seller. Owns a cart, selling list, order list, and notifications.
- `ManajerAkun`: in memory user registry and login verification.
- `Produk`: item for sale. Has price, stock, description, location, seller, and reviews.
- `Review`: rating and comment for a product.
- `Pemesanan`: order record with product, quantity, buyer, and shipping status.
- `Penawaran`: price offer created by a buyer for a product.
- `Chat`: message object and global chat history.
- `Notifikasi` and `NotifikasiInterface`: notification storage and interface.
- `Utils`: input helpers and currency formatting.

## Relationships and behavior

- A `PembeliPenjual` can be both a seller and a buyer.
- A `Produk` belongs to one seller and can have many reviews.
- A `Pemesanan` belongs to one buyer and refers to one product.
- A `Penawaran` links one buyer and one product and has a status.
- `Chat` history is shared by all users in a static list.

## Copy behavior

Several constructors and getters return new objects. This reduces accidental changes but can also make updates harder because you may be working with copies instead of the original instance.
