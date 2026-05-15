# Testing and Quality

## Unit tests

Tests are in src/test/java/secondhand_marketplace/app.

- `AppTest` is a simple placeholder test.
- `MenuTest` checks menu output, input parsing, and a basic register flow.

Run tests:

```bash
mvn test
```

## Static analysis

PMD and SpotBugs are configured in the Maven build. Run them with:

```bash
mvn verify
```

## Known gaps

- No integration tests for end to end flows.
- No tests for product, order, or review logic.
- No persistence tests because data is in memory only.
