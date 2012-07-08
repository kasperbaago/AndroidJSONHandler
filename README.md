<h1>Android Ajax Handler</h1>
====================

<h2>Introduction:</h2>
<p>
Android Ajax Handler is a library for Android, which make it easy to communicate with JSON apies on webservers. <br/>
The library extends the AsyncTask Android class, so it will work asyncrinisly with your code and not stop the whole exicution of your program.<br/>
You can self define the different dialog messages, using different methods in the the library.<br/>
You must know that the class is somehting I have written for own use, so don't expect it to be perfectly written an documentet :)
</p>

<h2>How to install the library:</h2>
<p>Download the "dk" folder, and import it into your "src" directory in your app.</p>

<h2>How to use:</h2>
<ol>
  <li>Create a new AjaxHandler object, and insert the context as parameter</li>
  <li>
    Use the method .makeRequest(String url, List<nameValuePairs> parameters, String method, String callback). <br/>
    The method takes four parameters as input:
    <ul>
      <li><b>String url:</b> The URL which should be called</li>
      <li>
        <b>List<nameValueParis> parameters:</b> Which parameters should be send with the call?(e.x POST and GET parameters)<br/>
        This is made using a list of nameValuePairs, where name is the key and value is the value of the reqest.
      </li>
      <li>
        <b>String method:</b> Which method that should be used to make the call("post" or "get").
      </li>
      <li>
        <b>String callback:</b> The method that should be called with the result of your request to the server. This method will be called an JSONArray as parameter.
      </li>
    </ul>
  </li>
</ol>


<h2>Example</h2>
<p>Comes soon..</p>