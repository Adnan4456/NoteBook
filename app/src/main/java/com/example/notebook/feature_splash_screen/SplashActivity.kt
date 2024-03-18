package com.example.notebook.feature_splash_screen

 import android.os.Bundle
 import androidx.activity.ComponentActivity
 import androidx.activity.compose.setContent
 import androidx.compose.foundation.*
 import androidx.compose.foundation.layout.*
 import androidx.compose.foundation.pager.HorizontalPager
 import androidx.compose.foundation.pager.PageSize
 import androidx.compose.foundation.pager.rememberPagerState
 import androidx.compose.foundation.shape.CircleShape
 import androidx.compose.foundation.shape.RoundedCornerShape
 import androidx.compose.material.icons.Icons
 import androidx.compose.material.icons.filled.KeyboardArrowLeft
 import androidx.compose.material.icons.filled.KeyboardArrowRight
 import androidx.compose.material3.*
 import androidx.compose.runtime.Composable
 import androidx.compose.runtime.SideEffect
 import androidx.compose.runtime.rememberCoroutineScope
 import androidx.compose.ui.Alignment
 import androidx.compose.ui.Modifier
 import androidx.compose.ui.draw.clip
 import androidx.compose.ui.graphics.Color

 import androidx.compose.ui.res.colorResource
 import androidx.compose.ui.text.TextStyle
 import androidx.compose.ui.unit.dp
 import androidx.compose.ui.unit.sp
 import com.example.notebook.R
 import com.example.notebook.rememberWindowSizeClass
 import com.example.notebook.feature_splash_screen.pages.ExplainPager
 import com.example.notebook.feature_splash_screen.pages.NotePager
 import com.example.notebook.feature_splash_screen.pages.TodoPager
 import com.google.accompanist.systemuicontroller.rememberSystemUiController
 import com.google.firebase.auth.FirebaseAuth
 import dagger.hilt.android.AndroidEntryPoint
 import kotlinx.coroutines.launch
 import javax.inject.Inject

@AndroidEntryPoint
class SplashActivity : ComponentActivity() {

    @Inject
    lateinit var firbaseAuth: FirebaseAuth

    @OptIn(ExperimentalFoundationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val user = firbaseAuth.currentUser
//        installSplashScreen()
//        val myIntent = Intent(this, MainActivity::class.java)
//        GlobalScope.launch {
//            launch {
//                if (user != null){
//                    startActivity(
//                        myIntent
//                    )
//                }
//                startActivity(myIntent)
//                finish()
//            }
//        }
//        val animationSpec = spring(
//            dampingRatio = Spring.DampingRatioMediumBouncy,
//            stiffness = Spring.StiffnessLow
//        )

//            val animatedPadding: Dp by animateFloatAsState<Float>(
//                targetValue = padding.value,
//                animationSpec = animationSpec
//            )
        setContent {
            val window = rememberWindowSizeClass()
//            val pagerState = rememberPagerState(initialPage = 0)
            val  pagerState = rememberPagerState(
                initialPage = 0,
                initialPageOffsetFraction = 0f,
                pageCount = {
                    3
                }
            )
            val scope = rememberCoroutineScope()

            val list = listOf(
                NotePager(),
                TodoPager(),
                ExplainPager()
            )

            val systemUiController = rememberSystemUiController()
                val darkTheme = isSystemInDarkTheme()
                SideEffect {
                    systemUiController.setSystemBarsColor(
                        color = if (darkTheme) Color.Black else Color.White
                    )
                }

            Box(
                modifier = Modifier
                    .fillMaxSize(),
            ){

                HorizontalPager(
                    state = pagerState,
                    key = {
                        index -> index
                    }
                ) {index ->
                    when(index){
                        0 -> NotePager()
                        1 -> TodoPager()
                        2 -> ExplainPager()
                    }
                }

//                HorizontalPager(
//                    pageCount = list.size,
//                    state = pagerState,
//                    key = { index -> index },
//                    pageSize = PageSize.Fill,
//                    modifier = Modifier.padding(top = 16.dp)
//                ) {index ->
//
//                    when(index){
//                        0 -> NotePager()
//                        1 -> TodoPager()
//                        2 -> ExplainPager()
//                    }
//                }

                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp, start = 16.dp, end = 16.dp),
                    contentAlignment = Alignment.TopStart

                ){

                    TextButton(
                            modifier = Modifier.align(Alignment.TopEnd),
                        onClick = {

                        }) {
                        Text(text = "Skip" ,
                            style = TextStyle(
                                fontSize = 16.sp,
                                color = colorResource(id = R.color.main_color)
                            )
                        )
                    }

                    Row(
                        modifier = Modifier
                            .align(Alignment.TopStart)
                            .clickable {
                                scope.launch {
                                    pagerState.animateScrollToPage(
                                        pagerState.currentPage - 1
                                    )
                                }
                            },
                        verticalAlignment = Alignment.CenterVertically,

                    ) {
                        if(pagerState.currentPage>0){

                            Icon(
                                imageVector = Icons.Default.KeyboardArrowLeft,
                                contentDescription = "",
                                tint = colorResource(id = R.color.main_color)
                            )
                            TextButton(
                                onClick = {
                                    scope.launch {
                                        pagerState.animateScrollToPage(
                                            pagerState.currentPage - 1
                                        )
                                    }
                                }) {
                                Text(text = "Back" ,
                                    style = TextStyle(
                                        fontSize = 16.sp,
                                        color = colorResource(id = R.color.main_color)
                                    )
                                )
                            }
                        }
                    }
                }

                Box(
                    modifier = Modifier
                        .offset(y = -(26).dp)
                        .fillMaxWidth()
                        .fillMaxHeight(.30f)
                        .align(Alignment.BottomCenter)

                ){

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight()
                            .padding(8.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.SpaceAround

                    ) {

                        Row(
                            Modifier
                                .wrapContentHeight()
                                .fillMaxWidth()
                                .padding(bottom = 16.dp),

                        ) {
                            repeat(3) { iteration ->
                                val color = if (pagerState.currentPage == iteration) colorResource(id = R.color.main_color) else Color.White
                                val width = if(pagerState.currentPage== iteration) 60.dp else 34.dp
                                Box(
                                    modifier = Modifier
                                        .padding(16.dp)
                                        .clip(CircleShape)
                                        .background(color)
                                        .width(width)
                                        .size(16.dp)
                                )
                            }
                        }
                        
                        if(pagerState.currentPage == 2){

                            Column() {

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = colorResource(id = R.color.main_color)
                                    ),
                                ) {
                                    
                                    TextButton(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = { 
                                        //Navigate to login screen
                                    }) {
                                        
                                        Text(text = "Create Account" ,
                                        style = TextStyle(
                                            color = Color.White
                                        ))
                                    }
                                }

                                Spacer(modifier = Modifier.height(8.dp))

                                Card(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(50.dp),
                                    shape = RoundedCornerShape(16.dp),
                                    colors = CardDefaults.cardColors(
                                        containerColor = Color.White
                                    ),
                                ) {

                                    TextButton(
                                        modifier = Modifier.fillMaxWidth(),
                                        onClick = {
                                            //Navigate to login screen
                                        }) {

                                        Text(text = "Sign in" ,
                                            style = TextStyle(
                                                color = colorResource(id = R.color.main_color)
                                            )
                                        )
                                    }
                                }
                            }
                        }

                        if(pagerState.currentPage < 2){

                            Card(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .height(50.dp),
                                shape = RoundedCornerShape(16.dp),
                                colors = CardDefaults.cardColors(
                                    containerColor = colorResource(id = R.color.main_color)
                                ),
                            ) {
                                IconButton(
                                    modifier = Modifier.fillMaxSize(),
                                    colors = IconButtonDefaults.iconButtonColors(
                                        containerColor = colorResource(id = R.color.main_color)
                                    ),
                                    onClick = {
                                        scope.launch {
                                            pagerState.animateScrollToPage(
                                                pagerState.currentPage + 1
                                            )
                                        }
                                    }) {
                                    Icon(
                                        imageVector = Icons.Default.KeyboardArrowRight,
                                        contentDescription = "",
                                        tint = Color.White
                                    )
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
@Composable
fun getFunction(){

}