import markdown
import StringIO

from datetime import date

files = ["design.md.txt"]

today = date.today()

head = "".join(open("head.md.txt","r").readlines())
tail = "".join(open("tail.md.txt","r").readlines())

for x in files:

	out = StringIO.StringIO()
	markdown.markdownFromFile(x,out,["codehilite"])

	out = out.getvalue().replace("$(date)",today.strftime("%Y-%m-%d"))
	out = out.replace("$(head)",head)
	out = out.replace("$(tail)",tail)
	open(x.replace("md.txt","html"),"w").write(out)
