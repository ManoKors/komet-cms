# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

## [Unreleased]

### Changed
- **Premium UI/UX Redesign:** Das komplette Interface wurde von einem "Tech-Prototyp" auf ein minimalistisches "Enterprise SaaS"-Niveau (inspiriert von Vercel/Linear) gehoben.
- **Farbpalette:** Die grüne Primärfarbe wurde durch ein elegantes monochromes Farbschema (Zinc-Töne) ausgetauscht.
- **Typografie:** Installation und Einbindung von `@fontsource/geist-sans` als primäre, scharfe UI-Schriftart.
- **Layout & Canvas:** Das Boxed-Layout wurde durch einen Seamless-Canvas (`bg-zinc-50`) ersetzt. Sektionen im Dashboard sind nun freischwebende Kacheln (`shadow-ghost-sm`).
- **Core-Komponenten:** `TextInput` und `SelectInput` wurden mit modernen Focus-Ringen und sauberen Border-Farben überarbeitet.
- **Interaktionen:** Dem Speichern-Button wurde ein SVG-Loading-Spinner (`isSaving`) hinzugefügt. Im `ListRepeater` ersetzt nun ein Grip-SVG (Drag&Drop-Andeutung) sowie ein Hover-basiertes Mülleimer-SVG die bisherigen Text-Buttons.

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
