# Video-Game-Discounts
Thanks to the help of JSoup, an open source Java library, we can successfully retrieve and parse HTMLs from various websites to read and store data. In the case of the VideoGame_Discounts program, the data includes deals and discounts in video games, and occasionally video game accessories. Using Java's Swing, we finally display our data in a window with some format. 

Finally, we can use all this to view all sorts of game deals from a variety of sources, in a single window, in real time.

The program can currently get data from 5 websites:
 - The first 100 items under Steam's Specials with the 'relevance' search tag
 - The items on the front page of the subreddit "https://www.reddit.com/r/GameDeals/new/"
 - The items on the front page of the website "https://www.cheapassgamer.com/"
 - The items under "Top Deals" and "Best Seller"  from the website "https://www.gamesdeal.com/"
 - The items from the website "https://www.greenmangaming.com/", including their deal of the day

The GUI is pretty simple, and is made of three parts using a BorderLayout. 
 - A JPanel containing a JLabel sits at the North section of the BorderLayout, and will tell you what website the program is parsing from (if you haven't parsed a site yet, it welcomes you to the main menu).
 - A JPanel for multiple sits at the South section of the BorderLayout; there's a button for each of the websites listed above, and an exit button that simply closes the window. At the moment of clicking one of the website buttons, a function within the program retrieves and parses the HTML of the website corresponding to the button you clicked, allowing us to read its data in real time and store it.
 - Finally, the Center section of the BorderLayout is occupied by A JScrollPane (which is of course holding a JList, which also happens to contain our data). The data we parse from websites is displayed inside the JScrollPane, and by extension to the user. Data is displayed one website at a time.

The rest of the program is in the ParseWebsites.java file, which is nothing more than a bunch of functions that retrieve and parse HTMLs from various websites to read and store data from said websites. These functions were made possible thanks to the JSoup library. (see "https://jsoup.org/") The GUI calls these parsing functions whenever a website button is pressed, and the data is stored and used to be displayed to the user.

PS: Try opening the program multiple times and pay attention to the title of the window :)

---------- Version 1.00 Notes ----------
 - parseCheap() function in ParseWebsites.java needs major polishing, it doesn't follow the format of the other parsing functions. Instead of using JSoup functions, I stored the parsed html in a string array, and read from there...its not very pretty. Needs fixing when possible.
 - Gamesdeal, GreenManGaming, and Steam deals could easily be displayed in a more organized fashion. Unfortunately that isn't the case for the subreddit and CheapAssGamer data, since its more informational news rather than numerical data. Even so, organization should be improved.
 - Maybe more websites could be added in the future?
