# API Dokumentation

Das Backend von Komet CMS ist eine Kotlin/Ktor-Anwendung. Es bietet eine REST-API unter dem Basis-Pfad `/api/v1`.
Dies ist der exakte aktuelle Ist-Zustand (aus `Routing.kt`).

## 1. Tenants Endpunkte

Verwalten die Mandanten (Agentur-Kunden).

### `GET /api/v1/tenants`
Ruft eine Liste aller Mandanten ab.
*   **Response (200 OK):**
    ```json
    [
      {
        "id": "tenant-123",
        "name": "Demo Praxis",
        "domain": "demo-praxis.komet.local",
        "webhookUrl": "https://hook.url/..."
      }
    ]
    ```

### `GET /api/v1/tenants/{tenantId}`
Ruft einen spezifischen Mandanten ab.
*   **Parameter:** `tenantId` (Pfad-Parameter)
*   **Response (200 OK):** JSON-Objekt des Tenants.
*   **Response (404 Not Found):** `{"error": "Tenant not found"}`

### `POST /api/v1/tenants`
Erstellt einen neuen Mandanten. Die Domain wird automatisch generiert.
*   **Request Body:**
    ```json
    {
      "name": "Neuer Kunde"
    }
    ```
*   **Response (201 Created):** JSON-Objekt des neuen Tenants.

### `PUT /api/v1/tenants/{tenantId}`
Aktualisiert die Daten eines Mandanten (aktuell nur `webhookUrl`).
*   **Parameter:** `tenantId` (Pfad-Parameter)
*   **Request Body:**
    ```json
    {
      "webhookUrl": "https://neue-webhook.url/hook"
    }
    ```
*   **Response (200 OK):** `{"success": "Tenant updated"}`

---

## 2. Content Endpunkte

Verwalten die eigentlichen JSON-Inhalte für jeden Mandanten basierend auf dem `blockType`.

### `GET /api/v1/content/{tenantId}/{blockType}`
Lädt den gespeicherten JSON-Inhalt für einen bestimmten Blocktyp eines Mandanten.
*   **Parameter:**
    *   `tenantId` (Pfad-Parameter)
    *   `blockType` (Pfad-Parameter, z.B. `hero`, `services`, `settings`)
*   **Response (200 OK):**
    ```json
    {
      "status": "success",
      "tenantId": "tenant-123",
      "data": { ... } // Der JSON Payload
    }
    ```
*   **Response (404 Not Found):** `{"status": "error", "tenantId": "tenant-123", "data": null}`

### `POST /api/v1/content/{tenantId}/{blockType}`
Speichert (Insert oder Update) den JSON-Inhalt für einen bestimmten Blocktyp und feuert asynchron den Webhook-Trigger, falls einer konfiguriert ist.
Das Backend validiert den Payload streng basierend auf dem `blockType`.

*   **Parameter:**
    *   `tenantId` (Pfad-Parameter)
    *   `blockType` (Pfad-Parameter)
*   **Request Bodys nach Blocktyp:**
    *   **Wenn `blockType` = `hero`** (`HeroPayload`):
        ```json
        {
          "title": "String",
          "subtitle": "String"
        }
        ```
    *   **Wenn `blockType` = `services`** (`ServicesPayload`):
        ```json
        {
          "items": [
            {
              "id": "String",
              "title": "String",
              "description": "String"
            }
          ]
        }
        ```
    *   **Wenn `blockType` = `settings`** (`SettingsPayload`):
        ```json
        {
          "theme": "String"
        }
        ```
*   **Response (200 OK):**
    ```json
    {
      "status": "success",
      "tenantId": "tenant-123",
      "message": "Content block saved successfully"
    }
    ```
