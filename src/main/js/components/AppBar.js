import React, {Fragment} from 'react';

export const AppBar = ({children}) => {
    return (
        <Fragment>
            <div className="mdc-drawer-app-content">
                <header className="mdc-top-app-bar app-bar" id="app-bar">
                    <div className="mdc-top-app-bar__row">
                        <section className="mdc-top-app-bar__section mdc-top-app-bar__section--align-start">
                            <span className="mdc-top-app-bar__title">WarehouseApp</span>
                        </section>
                    </div>
                </header>
                <main className="main-content" id="main-content">
                    <div className="mdc-top-app-bar--fixed-adjust">
                        {children}
                    </div>
                </main>
            </div>
        </Fragment>
    )
}