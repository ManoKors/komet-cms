# Changelog

All notable changes to this project will be documented in this file.

The format is based on [Keep a Changelog](https://keepachangelog.com/en/1.0.0/),
and this project adheres to [Semantic Versioning](https://semver.org/spec/v2.0.0.html).

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
