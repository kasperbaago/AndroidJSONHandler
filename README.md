<h1>Android Ajax Handler</h1>
====================

<h2>Introduction:</h2>
<p>
Android Ajax Handler is a library for Android, which make it easy to communicate with JSON apies on webservers. <br/>
The library extends the AsyncTask Android class, so it will work asyncrinisly with your code and not stop the whole exicution of your program.<br/>
You can self define the different dialog messages, using different methods in the the class.<br/>
You must know that the class is somehting I have written for own use, so don't expect it to be perfectly written an documentet :)
</p>

<h2>How to install the library:</h2>
<p>Download the "dk" folder, and import it into your "src" directory in your app.</p>

<h2>How to use:</h2>
<ol>
  <li>Create a new AjaxHandler object, and insert the context as parameter</li>
  <li>If you want to upload a file, use the method <i>setNameOfFileField(fieldname)</i> to set which field contains the file.</li>
  <li>Use <i>setDiag(tittle, message)</i> method, to set which text should be shown while the request is made.</li>
  <li>
    Error message is <b>default</b> set to be shown! If an error accurs, the callback will not be called and an error message will be shown to the user.<br/>
    The text of this error message can be set using <i>setErrMsg(tittle, message)</i>.<br/>
    If you do not want the error message to appear, and the callback to be called even if an error happens, use the <i>showErrMsg(true/false)</i> to disable the error message. 
    The callback will then be called with the last parameter set to true, if an error has accured.
  </li>
  <li>
    Use the method .makeRequest(String url, List<nameValuePairs> parameters, String method, String callback). <br/>
    The method takes four parameters as input:
    <ul>
      <li><b>String url:</b> The URL which should be called(ex. http://kasperbaago.dk/api/)</li>
      <li>
        <b>List<nameValueParis> parameters:</b> Set which parameters should be send with the call?(ex. POST and GET parameters)<br/>
        This is made using a list of nameValuePairs, where name is the key and value is the value of the reqest.<br/>
        You can then use ether $_POST[name] or $_GET[name] in PHP to get the values send with the request.
      </li>
      <li>
        <b>String method:</b> Which method that should be used to make the call("post" or "get").
      </li>
      <li>
        <b>String callback:</b> The method that should be called with the result of your request to the server.<br/>
        This method will be called with two parameters: The first is a JSONArray, containing the returned JSON data. The last is a boolean value, which is true if an error has accured during exicution.
      </li>
    </ul>
  </li>
  <li>
    The method <i>getHTML</i> can be used to return the clear text HTML of the callback. This is usefull to debuging.
  </li>
</ol>


<h2>Example of how to use:</h2>
<p>Comming soon..</p>