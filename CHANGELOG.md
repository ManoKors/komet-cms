# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

## [0.1.11] - 2026-07-10

### Added
- Level 3 Agentic Context in agents.md (Autonomous Behavior, Self-Correction, Conventional Commits, Security Guidelines).

## [0.1.10] - 2026-07-10

### Added
- **Level 2 Agentic Context:** `agents.md` um strikte Programmier-, Design- und Architektur-Gesetze erweitert.
  - Fixierung auf Svelte 5 Runes (`$state`, `$derived`, etc.) und Verbot von Svelte 4 Syntax.
  - Etablierung des Enterprise UI Design-Systems (Vercel/Linear Ästhetik) mit Tailwind-Tokens (`bg-zinc-50`, `bg-zinc-900`, `lucide-svelte`).
  - Architektur-Restriktionen (Komponenten in `src/lib/components/`, API-Calls in `+page.ts`, Schema-Definitionen in `Database.kt`).
  - Obligatorische Verifizierungs-Befehle (`npm run check`, `npm run format`, `./gradlew test`) vor Abschluss von Code-Änderungen.

## [0.1.9] - 2026-07-09

### Added
- **Agentic Context & Automatisierte Dokumentation:** Einführung einer `agents.md` Datei im Root-Verzeichnis, um den strategischen Pivot zu einem exklusiven, internen Agentur-Werkzeug zu manifestieren. Diese Datei definiert feste Leitplanken und erzwingt automatische Dokumentation bei zukünftigen Änderungen.
- **Dokumentations-Fundament:** Neuer `docs/` Ordner mit `architecture.md`, `database.md` und `api.md`, der den aktuellen technischen Ist-Zustand des Projekts dokumentiert.

## [0.1.8] - 2026-07-09

### Added
- **Webhook Infrastruktur (Phase 6):** Ktor Backend um Webhook-Trigger erweitert, damit externes Static Site Generation (SSG) z. B. bei Vercel oder Cloudflare Pages automatisch gestartet werden kann.
- **Datenbankschema:** `Tenants` Tabelle um nullable Spalte `webhook_url` erweitert.
- **Backend-Erweiterung:** Neuer Endpunkt `PUT /api/v1/tenants/{tenantId}` zum Aktualisieren der Webhook-URL integriert. GET und POST Routing für Tenants auf asynchrones Auslesen und Schreiben der URL erweitert.
- **Ktor Webhook-Client:** `ktor-client-cio` eingebunden und als Fire-and-Forget Coroutine nach erfolgreichem Inhalt-Upsert implementiert (verhindert blockieren des Content-Saves).
- **Frontend Settings:** "Build Webhook URL" Eingabefeld in den globalen Einstellungen unter `tenant/[tenantId]/+page.svelte` hinzugefügt und im bestehenden `Promise.all` Save-Prozess integriert.

## [0.1.7] - 2026-07-09

### Added
- **Multi-Tenancy Architektur (Phase 5):** Das CMS ist nun "Multi-Tenant"-fähig. Der hartcodierte Test-Mandant wurde durch dynamisches Routing (`/tenant/[tenantId]`) ersetzt.
- **Backend Mandanten-Endpunkte:** Neue Ktor Routen `GET /api/v1/tenants` und `POST /api/v1/tenants` zum Abrufen und Anlegen von Mandanten implementiert. Die Domain wird beim Anlegen via Slug generiert.
- **Dashboard Mandanten-Übersicht:** Die Startseite (`/`) zeigt nun eine elegante Übersicht aller Mandanten in Form von Kacheln. Ein neues Inline-Formular erlaubt das Anlegen neuer Mandanten.
- **Kontextabhängige Sidebar:** Die Sidebar-Navigation in `+layout.svelte` reagiert nun auf den gewählten Mandanten und zeigt kontextbezogene Links (inkl. "Zurück zu allen Mandanten").

## [0.1.6] - 2026-07-08

### Added
- **Backend TDD Infrastruktur:** Test-Driven Development Umgebung (JUnit & Ktor TestHost) im Backend eingerichtet.
- **API Integration Tests:** Erste Integrationstests (`RoutingTest.kt`) für die Hero- & Services-Endpunkte (inkl. Upsert-Logik, 404 und Validation) geschrieben.
- **Datenbank-Refactoring:** `initDatabase()` um einen `jdbcUrl` Parameter erweitert, um Testläufe sicher auf einer isolierten In-Memory-Datenbank auszuführen.

### Changed
- **Premium UI/UX Redesign:** Das komplette Interface wurde von einem "Tech-Prototyp" auf ein minimalistisches "Enterprise SaaS"-Niveau (inspiriert von Vercel/Linear) gehoben.
- **Farbpalette:** Die grüne Primärfarbe wurde durch ein elegantes monochromes Farbschema (Zinc-Töne) ausgetauscht.
- **Typografie:** Installation und Einbindung von `@fontsource/geist-sans` als primäre, scharfe UI-Schriftart.
- **Layout & Canvas:** Das Boxed-Layout wurde durch einen Seamless-Canvas (`bg-zinc-50`) ersetzt. Sektionen im Dashboard sind nun freischwebende Kacheln (`shadow-ghost-sm`).
- **Core-Komponenten:** `TextInput` und `SelectInput` wurden mit modernen Focus-Ringen und sauberen Border-Farben überarbeitet.
- **Interaktionen:** Dem Speichern-Button wurde ein SVG-Loading-Spinner (`isSaving`) hinzugefügt. Im `ListRepeater` ersetzt nun ein Grip-SVG (Drag&Drop-Andeutung) sowie ein Hover-basiertes Mülleimer-SVG die bisherigen Text-Buttons.

## [0.1.5] - 2026-07-07

### Added
- **Strategie-Wechsel:** Fokus auf "Function over Form" und den "Astro-Beweis" für die Headless-Architektur (Phase 3 "UX Polish" pausiert).
- **Astro-Setup:** Ein neues minimales Astro-Projekt im Verzeichnis `astro-site` hinzugefügt, port auf `4321` konfiguriert und TypeScript aktiviert.
- **Ktor-Integration:** Daten (Hero- und Services-Blöcke) werden über `fetch()` im `Promise.all` aus dem Ktor-Backend gezogen und als statisches HTML generiert. Es wurden Fallbacks für den Fall, dass das CMS nicht erreichbar ist, implementiert.

## [0.1.4] - 2026-07-07

### Added

- **Phase 3 abgeschlossen:** UX-Polish und Settings-Block für die künftige Astro-Generierung implementiert.
- Globales Toast-Notification-System in SvelteKit (`toast.svelte.ts` & `+layout.svelte`) hinzugefügt.
- Ghost-Design `SelectInput.svelte` Komponente für Dropdowns erstellt.
- Theme Switcher ("Globale Einstellungen") zum Dashboard hinzugefügt.
- Backend erweitert: Neue POST-Route & `@Serializable` Klasse `SettingsPayload` für den `settings`-Block.

## [0.1.3] - 2026-07-07

### Added

- Array-Logik in Svelte 5 für `<ListRepeater>` (Hinzufügen, Löschen, Two-Way-Binding mit `$bindable()`) implementiert.
- Ktor Backend-Routing erweitert: Dynamische Pfade (`/content/{tenantId}/{blockType}`) mit typsicherer Serialisierung je nach blockType (HeroPayload vs. ServicesPayload).
- Dashboard aktualisiert: Lädt und speichert nun sowohl Hero- als auch Services-Blöcke (via `Promise.all`).

## [0.1.2] - 2026-07-07

### Added

- ROADMAP.md aktualisiert mit einer wesentlich granulareren, abhakbaren Version.
- `<ListRepeater.svelte>` Skelett-Komponente im Ghost-Design für dynamische Listen erstellt.

## [0.1.1] - 2026-07-06

### Added

- ROADMAP.md erstellt, um die kommenden 5 Phasen des Projekts zu strukturieren.
- Datenkreislauf geschlossen: GET-Request im Backend implementiert, der den JSON-Payload lädt und als `ContentResponse` ausliefert.
- SvelteKit load-Funktion (`+page.ts`) hinzugefügt, um Hero-Daten abzurufen und bei 404-Fehlern elegante leere Defaults zurückzugeben.

### Fixed

- Backend POST-Request auf Upsert-Logik umgestellt (Update statt endlosem Insert), damit nicht bei jedem Speichern ein neuer Datenbankeintrag für den Hero-Block erzeugt wird.

## [0.1.0] - 2026-07-06

### Added

- Erste funktionierende End-to-End Verbindung (Svelte -> Ktor) über ein Hero-Block Formular auf dem Dashboard.
- Initiales SvelteKit Setup, Integration der Ghost Admin Design Tokens, Erstellung des globalen Layout-Skeletons und der <TextInput> Komponente.
- Initiales Ktor-Backend-Setup (Kotlin, JetBrains Exposed, SQLite, kotlinx-serialization) im `backend/` Verzeichnis hinzugefügt.
