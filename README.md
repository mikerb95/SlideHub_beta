# 🎯 SyncSlide

**Real-time presentation control system with multi-screen synchronization.**

Control your presentations from multiple synchronized devices in real-time. Includes a projection screen, mobile remote, presenter view with notes & timer, master control panel, and dual-mode demo screen.

---

## 🚀 Quick Start

### Prerequisites
- **Java 21** (LTS)
- **Maven 3.9+** (or use included wrapper)

### Run Locally

```bash
# Clone the repository
git clone https://github.com/your-repo/syncslide.git
cd syncslide

# Run with Maven wrapper
./mvnw spring-boot:run

# Or with Maven installed
mvn spring-boot:run
```

Open **http://localhost:8080** in your browser.

### Run with Docker

```bash
docker-compose -f docker/docker-compose.yml up --build
```

---

## 📺 Views

| Route | Description | Optimized For |
|-------|-------------|---------------|
| `/` | Redirect to `/showcase` | — |
| `/showcase` | Landing page with project docs | Desktop |
| `/slides` | Fullscreen projection screen | Projector / TV |
| `/remote` | Touch remote control | Smartphone (portrait) |
| `/presenter` | Presenter view with notes & timer | Laptop 13-15" |
| `/main-panel` | Master control panel with thumbnails | Tablet 11" (landscape) |
| `/demo` | Dual mode: slides OR live iframe | Second monitor |

---

## 🔌 REST API

| Method | Endpoint | Description |
|--------|----------|-------------|
| `GET` | `/api/slide` | Get current slide `{"slide": 1}` |
| `POST` | `/api/slide` | Set slide `{"slide": 3}` |
| `POST` | `/api/slide/next` | Next slide |
| `POST` | `/api/slide/prev` | Previous slide |
| `GET` | `/api/demo` | Get demo state |
| `POST` | `/api/demo` | Set demo mode: `{"mode":"slides"}` or `{"mode":"url","url":"/path"}` |
| `GET` | `/api/config` | Get configuration (totalSlides, notes, links) |
| `POST` | `/api/config/reload` | Reload config files at runtime |

---

## ⌨️ Keyboard Shortcuts

| Key | Action |
|-----|--------|
| `→` / `Space` | Next slide |
| `←` | Previous slide |
| `Home` | First slide |
| `End` | Last slide |
| `F` | Toggle fullscreen |
| `T` | Toggle timer (presenter view) |

---

## 📂 Project Structure

```
syncslide/
├── src/main/java/com/syncslide/
│   ├── SyncSlideApplication.java      # Main entry point
│   ├── controller/
│   │   ├── ApiController.java         # REST API endpoints
│   │   ├── PresentationController.java # View routes
│   │   └── ShowcaseController.java    # Landing page
│   ├── service/
│   │   ├── SlideService.java          # Slide state management
│   │   └── ConfigService.java         # Config file loading
│   ├── model/
│   │   ├── SlideState.java            # Slide state DTO
│   │   └── DemoState.java             # Demo mode DTO
│   └── config/
│       ├── CacheConfig.java           # Caffeine cache setup
│       └── WebConfig.java             # CORS & resource handlers
├── src/main/resources/
│   ├── application.yml                # Main config
│   ├── application-dev.yml            # Dev profile
│   ├── application-prod.yml           # Production profile
│   ├── templates/                     # Thymeleaf HTML views
│   ├── static/presentation/           # Slide images (Slide1.PNG, ...)
│   └── config/
│       ├── presenter-notes.json       # Notes per slide
│       └── demo-links.json            # Configurable demo links
├── docker/
│   ├── Dockerfile                     # Multi-stage build
│   └── docker-compose.yml
└── .github/workflows/ci-cd.yml       # GitHub Actions CI/CD
```

---

## 🎨 Adding Your Slides

1. Export your presentation as images named `Slide1.PNG`, `Slide2.PNG`, etc.
2. Place them in `src/main/resources/static/presentation/`
3. The system auto-detects the total number of slides
4. Edit `src/main/resources/config/presenter-notes.json` for your notes

---

## 🏗️ Tech Stack

- **Backend:** Spring Boot 3.2, Java 21
- **Templates:** Thymeleaf
- **CSS:** Bootstrap 5.3 + Tailwind CSS 3
- **Icons:** Font Awesome 6.5
- **Font:** Inter (Google Fonts)
- **Cache:** Caffeine (dev) / Redis (prod)
- **Build:** Maven
- **Container:** Docker
- **CI/CD:** GitHub Actions

---

## 🔧 Configuration

### Environment Variables

| Variable | Default | Description |
|----------|---------|-------------|
| `PORT` | `8080` | Server port |
| `SPRING_PROFILES_ACTIVE` | — | `dev` or `prod` |

### Custom Properties (`application.yml`)

```yaml
syncslide:
  slides-dir: static/presentation          # Slides directory
  presenter-notes-path: config/presenter-notes.json
  demo-links-path: config/demo-links.json
  polling-interval-ms: 1000                 # Frontend polling interval
```

---

## 🧪 Testing

```bash
# Run all tests
./mvnw test

# Run with verbose output
./mvnw test -Dspring-boot.test.randomPort=true
```

---

## 📜 License

MIT License
