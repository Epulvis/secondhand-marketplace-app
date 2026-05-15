# Overview

## Purpose

This project is a console based secondhand marketplace. It focuses on basic flows: user accounts, product listing, buying and selling, order tracking, chat, and reviews.

## High level flow

1. The app starts in `secondhand_marketplace.app.App`.
2. `Menu` shows the CLI options and routes actions.
3. `AkunManagement` handles register, login, logout, and listing users.
4. `BarangManagement` handles products, orders, reviews, chat, offers, and notifications.

## In memory data model

All data lives in memory inside objects:

- Users are stored in `ManajerAkun`.
- Items for sale are stored in `BarangManagement` and also in each seller.
- Orders are stored in each user as a list of `Pemesanan`.
- Chat history is stored in a static list in `Chat`.
- Notifications are stored in `Notifikasi` objects.

Because there is no persistence, the data resets every time the app is restarted.

## Input and output

`Utils` wraps a `Scanner` to read console input and provides formatting helpers such as `formatRupiah` for currency output. Most screens are plain text tables and prompts.

## Status values

Orders use these status values:

- Menunggu Dicheckout
- Diproses
- Dalam Pengiriman
- Tiba di Gudang
- Dikirim ke Alamat Pembeli
