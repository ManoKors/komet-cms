# 🤖 KOMET CMS - AI Agent Briefing

Hallo KI-Agent! Bevor du Code in diesem Repository schreibst, analysierst oder änderst, MUSST du diese Datei lesen und dich strikt an die folgenden Regeln halten.

## 1. Strategisches Ziel & Business Model

*   **KEIN Public SaaS:** Komet CMS ist ein proprietäres, internes Werkzeug für einen Freelance-Webdesigner. Es gibt keine Self-Service-Registrierung, kein Billing (Stripe), keine öffentlichen Pricing-Pages und kein komplexes Rollen-System (RBAC).
*   **Der Use-Case:** Der Webdesigner entwirft individuelle Webseiten (Astro) für Kunden und nutzt Komet CMS (Svelte/Ktor) lediglich, um diesen Kunden ein simples Backend zur Textänderung bereitzustellen.
*   **Fokus:** Maximales Premium-Gefühl im UI/UX (Vercel/Linear-Ästhetik) bei gleichzeitig minimaler technischer Komplexität unter der Haube.

## 2. Technische Leitplanken (Tech-Stack)

*   **Frontend:** SvelteKit 5 (Runes), Tailwind CSS (62,5% Font-Size, 1rem=10px), TypeScript.
*   **Backend:** Ktor (Kotlin) mit Netty, JetBrains Exposed, SQLite.
*   **Architektur-Regel (YAGNI):** Baue keine "auf Vorrat" erdachten Features. Halte das Backend dumm (CRUD & JSON Storage). Echte Hexagonale Architektur ist aktuell Over-Engineering und streng verboten.
*   **Test-Driven:** Das Backend ist durch Ktor Integration Tests (`RoutingTest.kt`) mit einer In-Memory SQLite (`jdbc:sqlite::memory:`) abgesichert. Neue Backend-Routen erfordern vorher neue Tests!

## 3. Strikte Agenten-Gesetze (Code & Design)

Diese Regeln lassen null Spielraum für Halluzinationen oder Abweichungen vom System und müssen bedingungslos eingehalten werden:

1.  **Strikte Code-Syntax (Svelte 5 Runes):**
    *   Schreibe in Svelte-Komponenten AUSSCHLIESSLICH Svelte 5 Runes (`$state`, `$derived`, `$props`, `$effect`).
    *   Die alte Svelte 4 Syntax (`export let`, `$:`) ist streng verboten und führt zur Ablehnung des Codes.
    *   Nutze für Event-Handler zwingend die neue Syntax (`onsubmit`, `onclick` anstatt `on:submit`).
2.  **Design-System & Tailwind-Tokens (Enterprise UI):**
    *   Das Design muss an Vercel/Linear erinnern: extrem sauber, monochrom und hochwertig.
    *   **Hintergründe:** Nutze hauptsächlich `bg-zinc-50` oder `bg-white`.
    *   **Primary Actions (Buttons):** IMMER sattes Schwarz (z. B. `bg-zinc-900 text-white hover:bg-black rounded-md`). Keine bunten Farben (wie Grün oder Blau) für Standard-Buttons!
    *   **Borders & Schatten:** Nutze feine Borders (`border-zinc-200`) und extrem weiche Schatten (`shadow-sm`, `shadow-md`), um Tiefe zu erzeugen.
    *   **Icons:** Verwende ausschließlich `lucide-svelte`.
3.  **Architektur & Dateistruktur:**
    *   Wiederverwendbare UI-Elemente (Inputs, Buttons, Layouts) MÜSSEN in `src/lib/components/` liegen.
    *   API-Calls im Frontend passieren primär in den `+page.ts` (Load-Funktionen), um Layout-Shifts zu vermeiden.
    *   Backend-Datenbankschemata (Tabellen, Spalten) dürfen ausschließlich in `backend/src/main/kotlin/com/komet/Database.kt` definiert oder geändert werden.
4.  **Verifizierungs-Befehle (CLI):**
    *   Bevor du Code als "fertig" markierst, MUSST du ihn lokal validieren:
    *   **Frontend:** Führe `npm run check` (für TypeScript-Typisierung) und `npm run format` aus.
    *   **Backend:** Führe `cd backend && ./gradlew test` aus, um sicherzustellen, dass keine Regressionen in der API aufgetreten sind.

## 4. Level 3: Autonomes Verhalten & Problemlösung

1. **"Look Before You Leap" (DRY-Prinzip):**
   Bevor du ein neues Feature oder eine UI-Komponente erstellst, MUSST du den bestehenden Code (insbesondere `src/lib/components/`) analysieren.
   Code-Duplikation ist streng verboten. Nutze und erweitere bestehende Komponenten, anstatt das Rad neu zu erfinden.
2. **Self-Correction & Fehlerbehebung:**
   Wenn ein lokaler Check (`npm run check`, `npm run lint` oder `./gradlew test`) fehlschlägt, ist dein Task NICHT beendet.
   Du darfst den Fehler nicht einfach nur dem User melden. Du MUSST die Fehlermeldung analysieren, den Code korrigieren und den Test erneut ausführen, bis alle Checks erfolgreich ("grün") durchlaufen. Erst dann darfst du den Code als "fertig" präsentieren.
3. **Git Hygiene (Conventional Commits):**
   Nutze für Commit-Messages zwingend das Conventional Commits Format.
   Beispiele: `feat: [Beschreibung]`, `fix: [Beschreibung]`, `chore: [Beschreibung]`, `docs: [Beschreibung]`.
4. **Security & Environment Variables:**
   Hartcodierte Secrets (API-Keys, Passwörter, externe Webhook-Live-URLs) sind im Quellcode strengstens verboten.
   Nutze immer Konfigurationsdateien oder `.env` Variablen, wenn es um sensible Daten geht.

## 5. Der Pflicht-Workflow für KI-Agenten

Bei jedem Task, den du ausführst, MUSST du folgenden Ablauf einhalten:

1.  **Lies den Code & die Docs:** Konsultiere den `/docs/` Ordner, um den aktuellen Status der Architektur zu verstehen.
2.  **Implementiere:** Schreibe den Code nach den oben genannten Leitplanken.
3.  **Dokumentiere (Zwingend!):** Bevor du einen Task abschließt, MUSST du die entsprechenden Dateien im `/docs/` Ordner, sowie die `CHANGELOG.md` und `ROADMAP.md` aktualisieren. Ein PR ohne Update der Dokumentation ist ungültig.
