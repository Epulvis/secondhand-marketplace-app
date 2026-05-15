# Secondhand Marketplace CLI

A simple command line application that simulates a secondhand marketplace. Users can register, log in, sell items, buy items, track orders, chat, and leave reviews. All data is stored in memory, so it resets every time the app runs.

## Table of Contents

- [Features](#features)
- [Requirements](#requirements)
- [How to Run](#how-to-run)
- [Quality Checks](#quality-checks)
- [Default Accounts](#default-accounts)
- [Project Structure](#project-structure)
- [Documentation](#documentation)
- [Notes and limitations](#notes-and-limitations)

## Features

- Register, login, and logout users
- Browse items for sale and buy items
- Checkout, track orders, and update shipping status
- Sell items and manage stock
- Leave reviews for purchased items
- Chat between users and keep chat history
- Make offers and send notifications

## Requirements

- Java 8 or newer
- Maven 3 or newer

## How to run

1. Run tests (optional):

```bash
mvn test
```

2. Build the jar:

```bash
mvn -DskipTests package
```

3. Run the app:

```bash
java -cp target/app-0.0.1-SNAPSHOT.jar secondhand_marketplace.app.App
```

## Quality checks

Static analysis is configured with PMD and SpotBugs. Run:

```bash
mvn verify
```

## Default accounts

The app starts with three users:
| Username | Password |
|----------|----------|
| user1 | user1 |
| user2 | user2 |
| user3 | user3 |

## Project structure

```bash
.
├── docs
│   ├── domain-model.md
│   ├── overview.md
│   ├── testing-and-quality.md
│   └── usage.md
├── pom.xml
├── README.md
└── src
    ├── main
    │   └── java
    │       └── secondhand_marketplace
    │           ├── akun                             # Account management
    │           │   └── ManajerAkun.java
    │           ├── app                              # CLI menu and flows
    │           │   ├── AkunManagement.java
    │           │   ├── App.java
    │           │   ├── BarangManagement.java
    │           │   └── Menu.java
    │           ├── exception                        # Custom Exception
    │           │   └── LoginException.java
    │           ├── Komunikasi                       # Chat and notification
    │           │   ├── Chat.java
    │           │   ├── NotifikasiInterface.java
    │           │   └── Notifikasi.java
    │           ├── pengguna                         # User model
    │           │   ├── PembeliPenjual.java
    │           │   ├── Penawaran.java
    │           │   └── Pengguna.java
    │           ├── produk                           # Product and review model
    │           │   ├── Produk.java
    │           │   └── Review.java
    │           ├── transaksi                        # Order model
    │           │   └── Pemesanan.java
    │           └── utils                            # Input and formatting helpers
    │               └── Utils.java
    └── test                                         # Unit tests
        └── java
            └── secondhand_marketplace
                └── app
                    ├── AppTest.java
                    └── MenuTest.java

18 directories, 24 files
```

## Documentation

- [docs/overview.md](docs/overview.md)
- [docs/usage.md](docs/usage.md)
- [docs/domain-model.md](docs/domain-model.md)
- [docs/testing-and-quality.md](docs/testing-and-quality.md)

## Notes and limitations

- Data is in memory only. There is no database or file storage.
- Many getters return copies of objects, so changes may not affect the original instance.
