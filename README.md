# Event Driven Reference Architecture

This project represents the body of knowledge around event-driven architecture and can be considered as a live book, we are writing from our consulting engagements. 
All the content is visible [as a book format here](https://ibm-cloud-architecture.github.io/refarch-eda).  

The content of this repository was the source of the event-driven reference architecture in the [IBM Garage architecture center visible here](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture). This git repository is maintained on a weekly basis and includes more content not yet formally published to IBM sites. As we are implementing the end to end solution we are updating this main git repository to keep best practices accurate.

Update 03/03/2019.

### Building this booklet locally

The content of this repository is written with markdown files, packaged with [MkDocs](https://www.mkdocs.org/) and can be built into a book-readable format by MkDocs build processes.

1. Install MkDocs locally following the [official documentation instructions](https://www.mkdocs.org/#installation).
2. `git clone https://github.com/ibm-cloud-architecture/refarch-eda.git` _(or your forked repository if you plan to edit)_
3. `cd refarch-eda`
4. `mkdocs serve`
5. Go to `http://127.0.0.1:8000/` in your browser.

### Pushing the book to GitHub Pages

1. Ensure that all your local changes to the `master` branch have been committed and pushed to the remote repository.
   1. `git push origin master`
2. Ensure that you have the latest commits to the `gh-pages` branch, so you can get others' updates.
	```bash
	git checkout gh-pages
	git pull origin gh-pages
	
	git checkout master
	```
3. Run `mkdocs gh-deploy` from the root refarch-ada directory.

--- 

## Contribute

We welcome your contributions. There are multiple ways to contribute: report bugs and improvement suggestion, improve documentation and contribute code.
We really value contributions and to maximize the impact of code contributions we request that any contributions follow these guidelines:

The [contributing guidelines are in this note.](./CONTRIBUTING.md)

## Project Status

* [10/2018] Started
* [11/2018] Implement ship simulator and stream analytics proof of concepts
* [01/2019] Publish content to IBM Architecture center
* [02/2109] Enhance design pattern and move content as book layout

## Contributors

* Lead developer [Jerome Boyer](https://www.linkedin.com/in/jeromeboyer/)
* Lead offerings [Andy Gibbs](https://www.linkedin.com/in/andy-g-3b7a06113/)
* [IBM Streams Analytics team]
  * [Martin Siegenthaler](https://www.linkedin.com/in/martin-siegenthaler-7654184/)
  * [David Engebretsen](https://www.linkedin.com/in/david-engebretsen/)
  * [Francis Parr](https://www.linkedin.com/in/francis-parr-26041924)
* [IBM Event Stream team]
* [IBM Decision Insight team]
  * [Jose De Freitas](https://www.linkedin.com/in/jose-de-freitas-755a501b/)
* [Hemankita Perabathini](https://www.linkedin.com/in/hemankita-perabathini/)

Please [contact me](mailto:boyerje@us.ibm.com) for any questions.
