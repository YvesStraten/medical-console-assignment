import os
import subprocess
import sys

dir = sys.argv[1]
fd = subprocess.getoutput("fd -e java -t f . " + dir)

output = ""
for line in fd.split("\n"): 
    path_split = os.path.split(line)[1]
    output += "\t\\textit{" + path_split + "}" + "\n" + "\t\\inputminted{java}{" + line + "}\n\n"

subprocess.run("pbcopy", input=output, encoding="ascii")

