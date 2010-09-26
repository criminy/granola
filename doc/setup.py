import markdown
import StringIO

from datetime import date

#TODO: use a search function to find all .md.txt files
files = ["design.md.txt"]

today = date.today()

head = "".join(open("template/head.md.txt","r").readlines())
tail = "".join(open("template/tail.md.txt","r").readlines())

for x in files:

   out = StringIO.StringIO()
   markdown.markdownFromFile(x,out,["codehilite"])

   out = out.getvalue().replace("$(date)",today.strftime("%Y-%m-%d"))
   out = head + out + tail
   out = out.replace("<p></p>","")
   out = out.replace("<p> </p>","") ## TODO: replace with regexp
   open(x.replace("md.txt","html"),"w").write(out)
