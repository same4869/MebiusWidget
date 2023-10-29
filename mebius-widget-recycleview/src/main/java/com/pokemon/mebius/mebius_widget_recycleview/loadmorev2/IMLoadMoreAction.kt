package com.pokemon.mebius.mebius_widget_recycleview.loadmorev2

interface IMLoadMoreAction {

    /**
     * 获取当前状态
     * @return [IMLoadMoreAction.Status]
     */
    fun getStatus(): Status

    /**
     * 更新状态
     */
    fun updateStatus(status: Status)

    /**
     * footView点击监听
     */
    fun setOnFootClickListener(listener: (Status) -> Unit)

    /**
     * 添加自定义foot，为添加的话自动应用[SoraDefFootContainer]
     */
    fun setFoot(foot: IMFootContainer)

    /**
     * 获取foot
     */
    fun getFoot(): IMFootContainer?

    /**
     * 加载更多监听
     */
    fun setOnLoadMoreListener(listener: () -> Unit)

    /**
     * 设置提前最后一个条目多少开始加载更多
     */
    fun setPreloadNum(Num: Int)

    /**
     * 隐藏底部
     */
    fun setFootVisibleOrGone(isShow: Boolean)

    /**
     * footer是否在可视区域
     */
    fun footerVisibleInScreen(): Boolean

    /**
     * 尝试进行加载更多
     * 目前用在有删除功能列表上，
     * 删除的时候调用该方法，
     * 当删除到列表底部foot可见时候触发加载更多，
     * 避免底部状态显示不正常情况
     */
    fun retryLoadMoreData()

    /**
     * 设置是否允许加载更多
     */
    fun setEnableLoadMore(enableLoadMore: Boolean)

    enum class Status {
        /**
         * 准备好了可以进行加载更多
         */
        READY,

        /**
         * 加载更多中
         */
        LOADING,

        /**
         * 没有更多数据
         */
        NO_MORE,

        /**
         * 发生错误，目前没有细分是什么错误如果有需要在扩展
         */
        ERROR
    }
}
