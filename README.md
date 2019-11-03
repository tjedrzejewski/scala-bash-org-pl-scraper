### bash.org.pl scraper

This scraper downloads entries from bash.org.pl

It works on two modes:
 - to download specific entry please provide as program arguments: ***entry***  as keyword and ***id*** of the entry e.g.:<br/>
 `scala Main entry 4862636`
 - to download latest pages please provide as program arguments: ***latest***  as keyword and ***number of pages*** which you want to download e.g.:<br/>
 `scala Main latest 5`
 
 When you use IntelliJ instead of command line go to *Run > Edit Configurations* menu setting. A dialog box will appear. Now you can add arguments to the Program arguments input field.
 
 Entries are saved to file. Filepath is defined by typesafe config which can be found in *src/main/resources/default.conf*