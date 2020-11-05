# Yelp Paging

![20200821_113150](https://user-images.githubusercontent.com/7444521/90908230-18e3a280-e3a2-11ea-9747-919efdc12e95.jpg)

![Screenshot_20200821-105901_WeedmapsChallenge](https://user-images.githubusercontent.com/7444521/90908519-80015700-e3a2-11ea-8313-d544c9bf0d2b.jpg)
 
### Clean Android Architecture
Clean architecture is the separation of concerns.  We want the “inner levels” meaning we start on the inside with the web/domain going through the use cases through the viewmodel to the view (outermost) to have no idea of what is going on in the classes that it calls across circles.

> The outer circle represents the concrete mechanisms that are specific to the platform such as networking and database access. Moving inward, each circle is more abstract and higher-level. The center circle is the most abstract and contains business logic, which doesn’t rely on the platform or the framework you’re using.

A unidirectional flow will ensure that because things are decoupled further and modularity remains a focus we will be able to increase the speed at which we add new features.  Librification becomes easier as well if we continue to scale the application and parts of our application need to be portable across apps.
Between our Use Case and domain we want to use interfaces for two reasons 

A Data layer which is of a higher, more abstract level doesn’t depend on a framework, lower-level layer.
The repository is an abstraction of Data Access and it does not depend on details. It depends on abstraction.

![final-architecture](https://user-images.githubusercontent.com/7444521/90908419-59432080-e3a2-11ea-9495-d9d42d1e640d.png)

### Dagger Android instead of vanilla dagger

#### Pros :
- For a fresh implementation Dagger-Android would be a great fit as it simplifies the steps needed and code required to get a dependency graph up and running with Android components and dependency injection.  
- It also adheres to SOLID programming principles making sure that Inversion of Control (a class should not know how it is being initialized) is present in our injection of dagger dependencies into an activity or fragment.

#### Cons :
- A deep understanding of dagger-android is needed to understand what is going on behind the scenes. 
- A large refactor and knowledge sharing would need to take place. We could no longer use Component interfaces being implemented by our main app component like CommonAppComponent.  These would need to explicitly be converted to a @SubModule

We’re going with a single module application because having to bridge across modules introduces another Dagger dependency graph and I didn’t have time for that.
We are going with Dagger-Android instead of vanilla Dagger because it creates specific objects for us that are introduced into the dependency graph using annotations rather than having to create them ourselves.

The focus of Dagger isn’t what the lines of code look like but how are we dividing our dependencies into their own graphs using @Subcomponents and introducing objects into the dependency graph using our @Modules classes and @Provides methods
  
We have a dependency graph that is handled by our Android Injectors which are supplied instances of our activities and fragments using @ContributesAndroidInjector which are then injected when needed using the encapsulated functionality of DaggerApplication. Visualized here as a number of Doctor Octopus arms, each arm allows the injection into a different Android component. The arms grabs instances when they are contributed to the grabs and then shoves them where they are needed. 

![pasted image 0](https://user-images.githubusercontent.com/7444521/90905754-a02f1700-e39e-11ea-9bb3-1bbfe76eed63.png)

We are less worried about adding scopes for Dagger object because we are using lifecycle aware components that know of their supposed lifetime along with additional annotations that are recognized by Kotlin language features.

### Additional Notes :
 
We decided on having the search entering and results all displayed in a single fragment to limit the complexity.  We want to be able to have our single viewmodel delegate all of the appropriate logic rather than having one viewmodel reference the other and managing that relationship in a limited amount of time.
There was also the option that when a user wants to search for their actual term that we go with Google recommended approach of Implementing a search interface this seemed outside of the scope for this project and we could take a piece with SearchView.  I like the idea of keeping search results within the same fragment and limiting navigation for the user.  Queries are not needed as this is being handled by the api we are outsourcing to.

#### In order to get an endless list we need to pagination and here are the problems it solves

- The list grows unbounded in memory, wasting memory as the user scrolls.
- We have to convert our results from Flow to LiveData to cache them, increasing the complexity of our code.
- If our app needed to show multiple lists, we'd see that there is a lot of boilerplate to write for each list.

Typically, your UI code observes a LiveData<PagedList> object (or, if you're using RxJava2, a Flowable<PagedList> or Observable<PagedList> object), which resides in your app's ViewModel. This observable object forms a connection between the presentation and contents of your app's list data.
  
In order to create one of these observable PagedList objects, pass in an instance of DataSource.Factory to a LivePagedListBuilder or RxPagedListBuilder object. A DataSource object loads pages for a single PagedList. The factory class creates new instances of PagedList in response to content updates, such as database table invalidations and network refreshes. The Room persistence library can provide DataSource.Factory objects for you, or you can build your own.

![download](https://user-images.githubusercontent.com/7444521/90906105-251a3080-e39f-11ea-8c3b-21dc5add7e1b.png)

#### I had the following questions during development but never heard back :/

1. During my interview with Matt he mentioned that currently you are
using Koin for dependency injection in your application but intending
to migrate to Dagger.  Do you mind if I use Dagger (from Google) in my
project?
2. Pagination is one of the criteria.  This is sometimes accomplished
with a local cache where we fetch paginated data while we backup the
cache with network requests.  Are we expecting local storage as part
of the deliverable or is that sort of optional?
3. Which leads into my next question.  It seems like there is a good
number of things to accomplish with the instructions "You may spend no
longer than 3 days to return back."  What would YOU like to see
completed as a priority in this project?
4. When I send the completed project in email would you prefer .apk or .aab?
