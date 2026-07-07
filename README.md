# Komet CMS (Dashboard)

## Philosophie

Komet CMS ist ein Headless-Backend-Dashboard für lokale B2B-Kunden (z. B. Arztpraxen, Handwerker).
**Wichtig:** Komet CMS ist **kein** visueller Pagebuilder (wie Wix oder Elementor). Es ist ein striktes Interface zur Eingabe strukturierter Daten, das sich optisch an der Klarheit und Eleganz des Ghost CMS Admin-Dashboards orientiert. Die finale Webseite wird später von einem separaten Astro-Projekt generiert. Fokus auf "Structured Content".

## Tech-Stack

- **SvelteKit** (mit Svelte 5)
- **Tailwind CSS**
- **Vite**
- **TypeScript**

## Architektur

Das Dashboard kommuniziert via JSON-Payloads an eine Ktor-API (Kotlin). Diese API triggert wiederum Astro-Builds auf Cloudflare. Wir nutzen strikte Typisierung durchgehend, um fehlerhafte Inhaltsblöcke zu vermeiden, bevor sie das Backend erreichen.

## Lokales Setup

### Frontend (SvelteKit)

Abhängigkeiten installieren:

```bash
npm install
```

Lokalen Entwicklungsserver starten:

```bash
npm run dev
```

### Backend (Ktor)

Das Backend liegt im Ordner `backend/` und ist ein eigenständiges Gradle-Projekt. Es verwendet Kotlin, Ktor und SQLite.

1. **In den Backend-Ordner wechseln:**

   ```bash
   cd backend
   ```

2. **Server starten:**
   Nutze den generierten Gradle Wrapper, um den Ktor-Server zu starten. Du musst Gradle nicht global installiert haben.
   ```bash
   ./gradlew run
   ```

Der Server startet auf `http://0.0.0.0:8080`.
Die SQLite-Datenbank (`komet_cms.db`) und das Schema werden beim ersten Start **automatisch initialisiert** (durch die Funktion `initDatabase()` über JetBrains Exposed).
