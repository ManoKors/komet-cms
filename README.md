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

Abhängigkeiten installieren:

```bash
npm install
```

Lokalen Entwicklungsserver starten:

```bash
npm run dev
```
