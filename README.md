# CSC 330 Stock Market Simulator

Stock Market Simulator is a single-player game that provides an educational exploration of how the US stock market works.

## Software Development Lifecycle

We are going to adhere to the Agile philosophy and scrum. See [Agile Manifesto](http://agilemanifesto.org/) and [Why Scrum](https://www.scrumalliance.org/why-scrum).

The development will be broken down into these 5 phases:

1. Requirements analysis
    * What is a stock?
    * How do we simulate the basic behavior of a stock market? 
    * What should be the main components of the application?
    * What are different types of stocks?
    * What is the scope of the project?

2. System Design
    * User interface
    * Class hierarchy to represent the stock market, types of stocks, and their behaviors
    * Basic mathematical and financial foundations of the stock market

3. Implementation and Unit Testing
    * In this part, the actual code is written and tested
    * As the code is implemented, the system design and requirements may change

4. Integration Testing
    * The modules are combined and tested as a group
    * Changes to the system design are done as needed

5. Quality Assurance/Presentation
    * Final testing of the product
    * PowerPoint/Keynote presentation of the product to the class

## Roadmap

See our Trello group for the roadmap.

## Code Philosophy
* Always use String.equalsIgnoreCase() when comparing strings (unless otherwise necessary).
* Defer exception handling to the object that is most responsible for the exception.