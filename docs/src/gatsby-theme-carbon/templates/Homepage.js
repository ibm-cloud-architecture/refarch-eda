import React from 'react';
import { HomepageBanner, HomepageCallout } from 'gatsby-theme-carbon';
import HomepageTemplate from 'gatsby-theme-carbon/src/templates/Homepage';
import { calloutLink } from './Homepage.module.scss';
import { withPrefix } from 'gatsby';

import Carbon from '../../images/event-driven-main.jpg';

const FirstLeftText = () => <p>What are Event-Driven Architectures?</p>;

const FirstRightText = () => (
  <p>
    Event-driven architectures provide a collection of loosely-coupled software components, cooperatively reacting to unending streams of events occurring throughout the world.
    <a
      className={calloutLink}
      href={withPrefix(`/introduction/overview/`)}
    >
      Overview →
    </a>
  </p>
);

const SecondLeftText = () => <p>IBM Automation Client Engineering</p>;

const SecondRightText = () => (
  <p>
    IBM™ is a bold, comprehensive approach to innovation and transformation 
    that brings designers and developers together with your business and 
    IT stakeholders to quickly create and scale new ideas that can dramatically
     impact your business.
    <a
      className={calloutLink}
      href="https://www.ibm.com/garage"
      target="_blank"
      rel="noopener noreferrer"
    >
      IBM Automation →
    </a>
  </p>
);

const BannerText = () => <h1>IBM Automation for Cloud Event-Driven Reference Architecture</h1>;

const customProps = {
  Banner: <HomepageBanner renderText={BannerText} image={Carbon} />,
  FirstCallout: (
    <HomepageCallout
      backgroundColor="#030303"
      color="white"
      leftText={FirstLeftText}
      rightText={FirstRightText}
    />
  ),
  SecondCallout: (
    <HomepageCallout
      leftText={SecondLeftText}
      rightText={SecondRightText}
      color="white"
      backgroundColor="#061f80"
    />
  ),
};

// spreading the original props gives us props.children (mdx content)
function ShadowedHomepage(props) {
  return <HomepageTemplate {...props} {...customProps} />;
}

export default ShadowedHomepage;
