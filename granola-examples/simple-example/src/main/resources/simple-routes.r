GET / granola.example.simple.SimpleController.plain
GET /xml granola.example.simple.SimpleController.xml template=templates/simple/simple.xml
GET /template granola.example.simple.SimpleController.template template=templates/simple/simple.html
GET /fruits granola.example.simple.SimpleController.fruitMain template=templates/simple/main.html
GET /fruits/{fruit} granola.example.simple.SimpleController.fruits template=templates/simple/image.html