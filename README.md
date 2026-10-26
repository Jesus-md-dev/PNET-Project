# Espacio Metrónomo — Booking System

Booking system for a music rehearsal space, built in four milestones for the Internet Programming course: from a web prototype to a REST API and an Android app.

![Node.js](https://img.shields.io/badge/Node.js-339933?style=flat-square&logo=nodedotjs&logoColor=white) ![Express](https://img.shields.io/badge/Express-000000?style=flat-square&logo=express&logoColor=white) ![MongoDB](https://img.shields.io/badge/MongoDB-47A248?style=flat-square&logo=mongodb&logoColor=white) ![Android](https://img.shields.io/badge/Android-3DDC84?style=flat-square&logo=android&logoColor=white) ![Java](https://img.shields.io/badge/Java-ED8B00?style=flat-square&logo=openjdk&logoColor=white)

## Milestones

| Folder | What it contains |
|---|---|
| `Hito 1` | Responsive web prototype (HTML, CSS, JavaScript) |
| `Hito 2` | REST API with Node.js, Express and MongoDB |
| `Hito 3` | Web client consuming the API |
| `Hito 4` | Native **Android app** in Java: list, create, edit and view bookings, highlighted dates and a home screen widget, plus its backend service |

`Bookings.postman_collection.json` has the API requests for testing.

## Notes

- Contact details on the web prototype and in the Android app are example values, not real ones.
- The REST services read the MongoDB connection string from the `MONGODB_URI` environment variable.
- The Android app reads its Google Maps API key from `MAPS_API_KEY` in `Hito 4/EspacioMetronomo/local.properties`, which is not committed.
- Photos in `Hito 1/img` (and the Android header) are public domain (CC0) images found through [Openverse](https://openverse.org/), replacing the originals of unknown licence. The icons are by Freepik from Flaticon, as credited in the page footer.
- Build outputs, submission zips, the design mock-ups and the tutorial PDF were removed from the repository.

| Image | Source (CC0) |
|---|---|
| `sala1` | [Drum kit before performance](https://www.flickr.com/photos/29507259@N02/5534410145) |
| `sala2` | [Guitar lying against amp](https://www.flickr.com/photos/29507259@N02/5534623021) |
| `sala3` | [Drummer Plays Drums Kit](https://www.flickr.com/photos/136375272@N05/33084915008) |
| `sala4` | [Jam Dreams 1979](https://www.flickr.com/photos/37996646802@N01/55060803634) |
| `sala5` | [Amp](https://www.flickr.com/photos/34167287@N05/23869269416) |
| `sala6` | [Aspirador](https://www.flickr.com/photos/62518311@N00/317373986) |
| `sala7` | [Piano keyboard](https://www.flickr.com/photos/135396164@N05/22384280069) |
| `header` | [Piano Keyboard](https://www.flickr.com/photos/29155878@N03/16481912744) |
| `bus` | [Bus stop Langerhuize](https://www.flickr.com/photos/14674348@N04/6486512427) |
| `tren` | [Light-play in Amsterdam Amstel train station](https://www.flickr.com/photos/104736837@N03/29246727294) |
| `edificio` | [Three Tuns Court 56-64 King Street](https://www.flickr.com/photos/132932913@N02/19306795158) |
| `playa` | [Porto de Galinhas](https://www.flickr.com/photos/51314820@N00/63733451) |

Coursework from my Computer Engineering degree at the University of Cádiz (Escuela Superior de Ingeniería). Made together with Claudia Silvestre.
