#!/usr/bin/env bash

pandoc Template.tex --reference-doc=Template.docx -o output.docx
killall Microsoft\ Word
open output.docx
