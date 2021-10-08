# Event Driven Reference Architecture

This project represents the body of knowledge around event-driven architecture and can be considered as a live book, we are writing from our consulting engagements. 
All the content is visible [as a BOOK format here](https://ibm-cloud-architecture.github.io/refarch-eda).  

The content of this repository was the source of the event-driven reference architecture in the [IBM Automation architecture center visible here](https://www.ibm.com/cloud/garage/architectures/eventDrivenArchitecture). This git repository is maintained on a weekly basis and includes more content not yet formally published to IBM sites. As we are implementing the end to end solution we are updating this main git repository to keep best practices accurate.

### Building this booklet locally

```sh
cd docs
npm install
npm run dev
```

If you do not want to update your computer or if gatsby is not compatible with the nodes environment use the dockerfile:

```sh
# Build the image
docker build -f scripts/Dockerfile -t eda-nodes-env .
# Then under the docs folder
docker run -ti  -v $(pwd):/home -p 8000:8000 eda-nodes-env bash
```

--- 

## Contribute

We welcome your contributions. There are multiple ways to contribute: report bugs and improvement suggestion, improve documentation and contribute code.
We really value contributions and to maximize the impact of code contributions we request that any contributions follow these guidelines:

The [contributing guidelines are in this note.](./CONTRIBUTING.md)

## Project Status

* [10/2018] Started
* [11/2018] Implement ship simulator and stream analytics proof of concepts
* [01/2019] Publish content to IBM Architecture center
* [02/2019] Enhance design pattern and move content as book layout
* [03/2019] Work on event-driven pattern and skill journey
* [06/2019] Minikube deployment
* [07/2019] Labs skill journey
* [q1/2021] MQ content and labs, EDA governance started, updated to Kafka technology content. 

## Contributors

* Lead developer [Jerome Boyer](https://www.linkedin.com/in/jeromeboyer/)
* Lead developer [Rick Osowski](https://www.linkedin.com/in/rosowski/)
* Content developer [Jesus Almaraz](https://www.linkedin.com/in/jesus-almaraz-hernandez/)
* Lead offerings [Andy Gibbs](https://www.linkedin.com/in/andy-g-3b7a06113/)

Please [contact me](mailto:boyerje@us.ibm.com) for any questions.
