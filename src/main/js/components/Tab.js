import React from 'react';

export const Tab = ({name, isActive, selected}) => {
    return (
        <button key={name} name={name} className={isActive === name ? "mdc-tab mdc-tab--active" : "mdc-tab"}
                role="tab" aria-selected="true" tabIndex="0" onClick={selected}>
          <span className="mdc-tab__content">
            {/*<span className="mdc-tab__icon material-icons" aria-hidden="true">favorite</span>*/}
              <span className="mdc-tab__text-label">{name}</span>
          </span>
            <span className={isActive === name ? "mdc-tab-indicator mdc-tab-indicator--active" : "mdc-tab-indicator"}>
            <span className="mdc-tab-indicator__content mdc-tab-indicator__content--underline"></span>
          </span>
            <span className="mdc-tab__ripple"></span>
        </button>
    )
}