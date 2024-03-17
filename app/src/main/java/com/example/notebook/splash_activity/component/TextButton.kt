package com.example.notebook.splash_activity.component

 import androidx.compose.foundation.Image
 import androidx.compose.foundation.background
 import androidx.compose.foundation.layout.Box
 import androidx.compose.foundation.layout.fillMaxWidth
 import androidx.compose.foundation.shape.RoundedCornerShape
 import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.draw.clip
 import androidx.compose.ui.graphics.vector.VectorPainter
 import androidx.compose.ui.res.colorResource
 import androidx.compose.ui.res.painterResource
 import com.example.notebook.R


@Composable
fun TextButtons(
    text : String,
    onClick : () -> Unit
) {

    TextButton(
        onClick = {
            onClick.invoke()
        }) {
        Text(text = text)
    }
}

@Composable
fun ImageView(
    resource: Int,
    contentDescription: String,
    modifier: Modifier
){

    Image(
        modifier = modifier.fillMaxWidth(),
        painter = painterResource(id = resource),
        contentDescription = contentDescription)
}

@Composable
fun MyBox(){

    Box(
        modifier = Modifier.fillMaxWidth()
            .background(colorResource(id =R.color.box_color))
            .clip(RoundedCornerShape(
                topStartPercent = 10,
                topEndPercent = 10
            )),
    ){

    }
}