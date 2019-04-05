import React from 'react';

export const TabBar = (props) => {
    return (
        <div className="mdc-tab-bar" role="tablist">
            <div className="mdc-tab-scroller">
                <div className="mdc-tab-scroller__scroll-area">
                    <div className="mdc-tab-scroller__scroll-content">
                        {props.children}
                    </div>
                </div>
            </div>
        </div>
    );
}

