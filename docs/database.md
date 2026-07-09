# Datenbank-Schema

Das Komet CMS nutzt eine SQLite-Datenbank. Das Schema wird über das JetBrains Exposed ORM (Kotlin) verwaltet.
Im Folgenden ist der exakte aktuelle Ist-Zustand des Schemas (aus `Database.kt`) dokumentiert.

## Tabellen

### 1. `Tenants`

Speichert die konfigurierten Mandanten (Agentur-Kunden).

*   **`id`**: `varchar("id", 255)` - (Primary Key / Entity ID) Die eindeutige UUID des Mandanten.
*   **`name`**: `varchar("name", 255)` - Der Name des Mandanten (z.B. "Demo Praxis").
*   **`domain`**: `varchar("domain", 255)` - Eine generierte Domain für den Mandanten (z.B. "demo-praxis.komet.local").
*   **`webhook_url`**: `varchar("webhook_url", 255).nullable()` - (Optional) URL, die per POST-Webhook angesteuert wird, sobald Content gespeichert wird.

### 2. `ContentBlocks`

Speichert die eigentlichen Inhalte als schemalose JSON-Strings, verknüpft mit dem jeweiligen Tenant.

*   **`id`**: `integer` - (Primary Key, Auto-Increment) Die interne ID des Blocks.
*   **`tenant_id`**: `reference("tenant_id", Tenants)` - Foreign Key auf die `id` der Tabelle `Tenants`.
*   **`page_route`**: `varchar("page_route", 255)` - Der zugehörige Seiten-Pfad (Standard: `/`).
*   **`block_type`**: `varchar("block_type", 100)` - Der Typ des Blocks (z.B. `hero`, `services`, `settings`).
*   **`json_payload`**: `text("json_payload")` - Der tatsächliche Payload als JSON-String.
*   **`sort_order`**: `integer("sort_order").default(0)` - Ein optionales Sortierkriterium.
