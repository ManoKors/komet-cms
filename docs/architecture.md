# Architektur-Übersicht

Das Komet CMS ist als Headless CMS System konzipiert, das eine klare Trennung zwischen dem Editor für die Inhalte (CMS) und der eigentlichen Auslieferung der Website (SSG) aufweist. Die Architektur besteht aus drei wesentlichen Komponenten:

## Datenfluss

1.  **SvelteKit Frontend (CMS-Editor):**
    *   Die UI, in der Mandanten (Agentur-Kunden) ihre Inhalte verwalten.
    *   Es ruft Daten über RESTful APIs vom Ktor-Backend ab und sendet Aktualisierungen an dieses.
    *   Bietet Multi-Tenancy-Unterstützung durch dynamisches Routing (z.B. `/tenant/[tenantId]`).

2.  **Ktor Backend SQLite (Datenhaltung):**
    *   Das Backend, geschrieben in Kotlin mit Ktor, dient als JSON-Speicher.
    *   Es nutzt SQLite und JetBrains Exposed für die Datenhaltung.
    *   Das Backend ist simpel gehalten (CRUD) und validiert bzw. speichert den Payload der Blöcke in einer zentralen Tabelle.

3.  **Webhook Trigger:**
    *   Nachdem neue Inhalte (Content-Blöcke) im Backend erfolgreich gespeichert wurden (und ein `webhook_url` für den Tenant hinterlegt ist), sendet das Ktor Backend asynchron einen Webhook-POST-Request.
    *   Dieser Webhook signalisiert externen Diensten, dass sich Inhalte geändert haben.

4.  **Astro SSG (Auslieferung):**
    *   Ein statischer Site Generator (SSG) wie Astro (oder externe Plattformen wie Vercel / Cloudflare Pages) lauscht auf diesen Webhook.
    *   Beim Build-Vorgang zieht sich der SSG die aktuellsten JSON-Daten aus der Ktor API und generiert pures statisches HTML.
    *   **Trennung:** Die generierte Seite wird komplett entkoppelt vom CMS an den Endnutzer ausgeliefert. Das CMS muss im Live-Betrieb nicht für Traffic zur Verfügung stehen.
