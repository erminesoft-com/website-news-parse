<h2>Web parser to get news from most sites <a href="http://news-feed.erminesoft.co">Working Example</a></h2>

<p>
List of libraries:
</p>
<ul>Backend:
<li>Spring boot</li>
<li>Postgresql</li>
<li>Hibernate</li>
<li>JPA</li>
<li>Jsoup library</li>
<li>Apache POI</li>
</ul>
<ul>Frontend:
<li>AngularJS</li>
<li>JavaScript</li>

<h3>Install</h3>
<p>
Create database, copy folder face on apache server, build jar file (gradlew build)
run jar file (java -jar ./build/libs/site-parser-1.0.jar)
</p>

<h3>Manual</h3>
1. You need to get 1 news block in HTML code.<br>
2. Take from this code title, link, image link(if present), description(if present), time (if present).<br>
3. Obtain list correct articles.<br>
4. Save configuration.<br>

<a href="http://news-feed.erminesoft.co">Working Example</a>