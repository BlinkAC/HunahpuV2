Esta es una segunda versión de un proyecto anterior que se encunetra en mi repositorio igualmente, se suponia era un comparado de precios de productos de supermercados.
No hay mucho que agregar, en si se mejoraron muchos aspectos (Entre ellos el lenguaje de programación) y se probaron funciones nuevas que se implementaron en mi curva
de aprendizaje con kotlin como las corrutinas, viewBinding, paginación, entre otras.

Para ser sincero por cuestiones de tiempo y apesar de ser una versión "mejorada" hubo algunos metodos deprecados que se tuvieron que utlizar, así como librerias de las que
no se tenia un conocimiento apropiado para su uso (EX. las librerias Kapt)

Adicional a lo realizado a este proyeto se realizo un cronograma donde se explica un proyecto adicional realizado con .NET y alojado en azure (Quizá ya no este en funcionamiento)
[Cronograma de aplicación .pdf](https://github.com/BlinkAC/HunahpuV2/files/8145570/Cronograma.de.aplicacion.pdf)


Las librerias utilizadas fueron:

//firebase dependecies
// Import the BoM for the Firebase platform
implementation platform('com.google.firebase:firebase-bom:29.0.3')

// Declare the dependency for the Firebase Authentication library
// When using the BoM, you don't specify versions in Firebase library dependencies
implementation 'com.google.firebase:firebase-auth-ktx'

// Also declare the dependency for the Google Play services library and specify its version
implementation 'com.google.android.gms:play-services-auth:20.0.1'
    
//facebook login
implementation 'com.facebook.android:facebook-android-sdk:[4,5)'

// Fragment
implementation "androidx.fragment:fragment-ktx:1.3.2"

// Activity
implementation "androidx.activity:activity-ktx:1.2.2"

// ViewModel
implementation "androidx.lifecycle:lifecycle-viewmodel-ktx:2.3.1"

// LiveData
implementation "androidx.lifecycle:lifecycle-livedata-ktx:2.3.1"

// Retrofit
implementation "com.squareup.retrofit2:retrofit:2.9.0"
implementation "com.squareup.retrofit2:converter-gson:2.9.0"
implementation "com.squareup.okhttp3:logging-interceptor:4.9.3"

//Corrutinas
implementation 'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.3.6'

//RecyclerView
implementation 'androidx.recyclerview:recyclerview:1.2.0'

//Picasso
implementation "com.squareup.picasso:picasso:2.71828"
implementation 'androidx.legacy:legacy-support-v4:1.0.0'

//Room DB
implementation("androidx.room:room-runtime:2.4.0")
implementation 'androidx.room:room-ktx:2.4.0'
annotationProcessor("androidx.room:room-compiler:2.4.0")
kapt("androidx.room:room-compiler:2.4.0")

//BarcodeScanner
implementation('com.journeyapps:zxing-android-embedded:4.3.0') { transitive = false }
implementation 'com.google.zxing:core:3.3.0'

//Pagination
implementation("androidx.paging:paging-runtime:3.0.0-beta02")

//Location
implementation 'com.google.android.gms:play-services-location:19.0.1'

//DataStore
implementation 'androidx.datastore:datastore-preferences:1.0.0-alpha06'
