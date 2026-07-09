# Komet CMS - Projekt-Roadmap

## Phase 1: Fundament & End-to-End (Abgeschlossen)

- [x] SvelteKit 5 Frontend initialisieren (TypeScript, Tailwind, ESLint).
- [x] Ktor Backend initialisieren (Kotlin, Gradle, Netty).
- [x] Ghost-UI Design Tokens in Tailwind konfigurieren (Colors, Shadows, 62.5% Font-Size).
- [x] Svelte-Layout-Skeleton (`.gh-canvas` Prinzip) und `<TextInput>` erstellen.
- [x] SQLite Datenbank-Schema (`Tenants`, `ContentBlocks`) via JetBrains Exposed anlegen.
- [x] Ktor: POST-Route mit typsicherem JSON-Payload & Upsert-Logik (Update or Insert) implementieren.
- [x] Ktor: GET-Route mit Wrapper-Objekt (`ContentResponse`) implementieren.
- [x] SvelteKit: End-to-End Kreislauf schließen (Daten via `load` laden und via `onsubmit` speichern).

## Phase 2: Komplexe UI & Array-Handling (Aktuell)

- [x] Erstellung der `<ListRepeater.svelte>` Komponente im Ghost-Style.
- [x] Logik in Svelte implementieren: Hinzufügen, Bearbeiten und Löschen von Listen-Einträgen.
- [x] Ktor: Backend-Datenmodell für Array-Payloads (z.B. `List<TherapieLeistung>`) erweitern.
- [x] End-to-End Test: Dynamische Liste speichern und neu laden.

## Phase 3: UX Polish & State Management (Pausiert)

- [x] Implementierung eines globalen Toast-Notification-Systems (z.B. "Erfolgreich gespeichert").
- [x] Lade-Indikatoren (Spinner/Disabled States) während der HTTP-Requests.
- [x] Theme Switcher: Globale Einstellungen mit Dropdown zur Auswahl des Astro-Themes.

## Phase 4: Der Astro-Beweis (SSG) (Aktuell)

- [x] Setup eines minimalen Astro.build Projekts.
- [x] Automatisches Rendern der Ktor-JSON-Daten in pures HTML.

## Phase 4.5: Backend TDD & Test-Infrastruktur

- [x] JUnit & Ktor TestHost Setup.
- [x] API Integration Tests (Hero & Services).

## Phase 5: Mandanten-Auswahl & Routing

- [ ] Dynamische URL-Struktur im Svelte-Dashboard aufbauen (z.B. `/dashboard/[tenantId]/[page]`).
- [ ] Funktionale Sidebar mit Navigation (Startseite, Leistungen, Team).

## Phase 6: Deployment & Webhooks

- [ ] Ktor feuert POST-Webhook an Cloudflare nach erfolgreichem Speichern.
- [ ] Lokales/VPS Hosting-Setup dokumentieren.
