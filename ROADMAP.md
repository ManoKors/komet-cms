# Komet CMS - Projekt-Roadmap

Diese Roadmap dient als Kompass für die zukünftigen Sprints und Entwicklungsphasen des Komet CMS.

## Phase 1: Fundament & End-to-End (Aktuell)
* Setup von SvelteKit und Ktor.
* Integration der Ghost-UI Design Tokens (z.B. `bg-primary`, `rounded-ghost`).
* SQLite Datenbank-Speicherung mit JetBrains Exposed.
* GET/POST Datenkreislauf für einen einfachen "Lego-Stein" (Hero-Block).

## Phase 2: Komplexe UI & Repeater (Demnächst)
* Entwicklung der dynamischen `<ListRepeater>` Svelte-Komponente (z.B. für Therapie-Leistungen).
* Array-Handling im JSON-Payload für wiederholbare Inhalte.

## Phase 3: Mandanten-Auswahl & Routing
* Dashboard-Sidebar funktional machen.
* Dynamische Mandanten-IDs im Frontend implementieren.
* Navigation zwischen verschiedenen Seiten ("Startseite" und "Leistungen").

## Phase 4: Der Astro-Generator
* Entwicklung eines Skripts zur Generierung der statischen Astro-Seiten.
* Datenbasis für die Generierung ist die zentrale SQLite-Datenbank.

## Phase 5: Cloudflare & Webhooks
* Ktor Backend triggert Cloudflare-Builds automatisch bei erfolgreichem "Speichern".
