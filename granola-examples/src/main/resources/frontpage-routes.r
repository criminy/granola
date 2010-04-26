# Front page
GET / granola.example.FrontPageController.index

# Serve some static files from the root /media in the classpath
GET /media/{file} granola.mvc.generic.SimpleMediaFileController.serve root=media