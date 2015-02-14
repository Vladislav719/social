/**
 * Created by ElessarST on 14.02.2015.
 */
app.service('ApiHelpService', function(){
   return {
       getValueOrEmpty: function(value){
           if (value == null || value == undefined)
               return "";
           else
            return value;
       }
   }
});
