import android.annotation.SuppressLint
import android.util.Log
import android.widget.Toast
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.notebook.R
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.compose.rememberNavController
import com.example.notebook.feature_note.presentation.util.Screen


@Composable
fun FilterFabMenuButton(
    item: FilterFabMenuItem,
    onClick: (FilterFabMenuItem) -> Unit,
    modifier: Modifier = Modifier
) {


    FloatingActionButton(
        modifier = modifier,
        onClick = {
            onClick(item)
        },
//        backgroundColor = colorResource(
//            id = R.color.primary_color
//        )
    ) {
        Icon(
            painter = painterResource(item.icon), contentDescription = null, tint = colorResource(
                id = R.color.white
            )
        )
    }
}
@Composable
fun FilterFabMenuLabel(
    label: String,
    modifier: Modifier = Modifier
) {

    Surface(
        modifier = modifier,
        shape = RoundedCornerShape(6.dp),
        color = Color.Black.copy(alpha = 0.8f)
    ) {
        Text(
            text = label, color = Color.White,
            modifier = Modifier.padding(horizontal = 20.dp, vertical = 2.dp),
            fontSize = 14.sp,
            maxLines = 1
        )
    }

}

@Composable
fun FilterFabMenuItem(
    menuItem: FilterFabMenuItem,
    onMenuItemClick: (FilterFabMenuItem) -> Unit,
    modifier: Modifier = Modifier
) {

    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        //label
//        FilterFabMenuLabel(label = "Add Notes")
        FilterFabMenuLabel(label = "Add Todo")

        //fab
        FilterFabMenuButton(item = menuItem, onClick = onMenuItemClick)

    }

}

@Composable
fun FilterFabMenu(
    visible: Boolean,
    items: List<FilterFabMenuItem>,
    modifier: Modifier = Modifier,
    navController: NavController
) {


    val enterTransition = remember {
        expandVertically(
            expandFrom = Alignment.Bottom,
            animationSpec = tween(150, easing = FastOutSlowInEasing)
        ) + fadeIn(
            initialAlpha = 0.3f,
            animationSpec = tween(150, easing = FastOutSlowInEasing)
        )
    }

    val exitTransition = remember {
        shrinkVertically(
            shrinkTowards = Alignment.Bottom,
            animationSpec = tween(150, easing = FastOutSlowInEasing)
        ) + fadeOut(
            animationSpec = tween(150, easing = FastOutSlowInEasing)
        )
    }


    AnimatedVisibility(visible = visible, enter = enterTransition, exit = exitTransition) {
        Column(
            modifier = modifier.fillMaxWidth(), horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(16.dp),
        ) {
            items.forEach { menuItem ->
                FilterFabMenuItem(
                    menuItem = menuItem,
                    onMenuItemClick = {
//                        Toast.makeText()
                        Log.d("TAG","${it.label}")
                        if(it.label.equals("Note")){
                         navController.navigate(Screen.AddEditNoteScreen.route)
                        }
                        else{
                            navController.navigate(Screen.AddTodoScreen.route)
                        }
                    }
                )
            }
        }
    }
}

@Composable
fun FilterFab(
    state: FilterFabState,
    rotation:Float,
    onClick: (FilterFabState) -> Unit,
    modifier: Modifier = Modifier
) {
    FloatingActionButton(
        modifier = modifier
            .rotate(rotation),
        elevation = FloatingActionButtonDefaults.elevation(defaultElevation = 0.dp),
        onClick = {
            onClick(
                if (state == FilterFabState.EXPANDED) {
                    FilterFabState.COLLAPSED
                } else {
                    FilterFabState.EXPANDED
                }
            )
        },
//        backgroundColor = colorResource(
//            R.color.primary_color
//        ),
        shape = CircleShape
    ) {
        Icon(
            painter = painterResource(R.drawable.add_photo),
            contentDescription = null,
            tint = Color.White
        )
    }

}
@SuppressLint("UnusedTransitionTargetStateParameter")
@Composable
fun FilterView(
    items: List<FilterFabMenuItem>,
    modifier: Modifier = Modifier,
    navController: NavController
) {

//    var filterFabState by rememberSaveable {
//        mutableStateOf(FilterFabState.COLLAPSED)
//    }



    var  filterFabState by remember {
       mutableStateOf(FilterFabState.COLLAPSED)
    }
    val transitionState = remember {
        MutableTransitionState(filterFabState).apply {
            targetState = FilterFabState.COLLAPSED
        }
    }

    val transition = updateTransition(targetState = transitionState, label = "transition")

    val iconRotationDegree by transition.animateFloat({
        tween(durationMillis = 150, easing = FastOutSlowInEasing)
    }, label = "rotation") {
        if (filterFabState == FilterFabState.EXPANDED) 230f else 0f
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp), horizontalAlignment = Alignment.End,
        verticalArrangement = Arrangement.spacedBy(16.dp,Alignment.Bottom)
    ) {
        FilterFabMenu(items = items, visible = filterFabState == FilterFabState.EXPANDED , navController = navController )
        FilterFab(
            state = filterFabState,
            rotation = iconRotationDegree, onClick = { state ->
                filterFabState = state
            })
    }
}

data class FilterFabMenuItem(
    val label: String,
    val icon: Int
)
enum class FilterFabState {
    COLLAPSED,
    EXPANDED
}